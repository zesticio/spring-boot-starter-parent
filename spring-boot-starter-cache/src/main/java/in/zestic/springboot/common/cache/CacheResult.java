package in.zestic.springboot.common.cache;

import java.time.Duration;
import java.util.concurrent.*;

public class CacheResult {

    private final CompletionStage<ResultData> future;
    private volatile CacheResultCode resultCode;
    private volatile Duration timeout = Duration.ofMillis(1000);

    private volatile String message;

    public CacheResult(CompletionStage<ResultData> future) {
        this.future = future;
    }

    public CacheResult(CacheResultCode resultCode, String message) {
        this(CompletableFuture.completedFuture(new ResultData(resultCode, message, null)));
    }

    public CacheResult(Throwable ex) {
        future = CompletableFuture.completedFuture(new ResultData(ex));
    }

    public boolean isSuccess() {
        return getResultCode() == CacheResultCode.SUCCESS;
    }

    public CacheResultCode getResultCode() {
        waitForResult();
        return resultCode;
    }

    public void waitForResult(Duration timeout) {
        if (resultCode != null) {
            return;
        }
        try {
            ResultData resultData = future.toCompletableFuture().get(
                    timeout.toMillis(), TimeUnit.MILLISECONDS);
            fetchResultSuccess(resultData);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            fetchResultFail(e);
        }
    }

    protected void waitForResult() {
        waitForResult(timeout);
    }

    protected void fetchResultSuccess(ResultData resultData) {
        message = resultData.getMessage();
        resultCode = resultData.getResultCode();
    }

    protected void fetchResultFail(Throwable e) {
        message = e.getClass() + ":" + e.getMessage();
        resultCode = CacheResultCode.FAIL;
    }

    public CompletionStage<ResultData> future() {
        return future;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }
}