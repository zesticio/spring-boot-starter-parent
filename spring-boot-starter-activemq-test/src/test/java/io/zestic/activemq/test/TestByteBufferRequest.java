package io.zestic.activemq.test;

import io.zestic.coin.utils.Passport;
import io.zestic.core.pdu.ByteBuffer;
import io.zestic.core.pdu.ByteBufferRequest;
import io.zestic.core.pdu.Constants;
import io.zestic.core.pdu.ValueNotSetException;

import java.util.concurrent.atomic.AtomicInteger;

public class TestByteBufferRequest {

    public static AtomicInteger sequenceNumber = new AtomicInteger(0);

    public static void main(String[] args) throws ValueNotSetException {
        ByteBufferRequest request = new ByteBufferRequest();
        request.setCorrelationId(Passport.generate(System.currentTimeMillis()));
        request.setSequenceNumber(sequenceNumber.incrementAndGet());
        request.setCommandStatus(Constants.STATUS_OK);

        Sample model = new Sample();
        model.setId(1000000);
        model.setCollectionId(1000000);
        model.setProductId(1000000);
        String json = model.toJson();
        request.setPayload(json);
        request.setHash(Passport.generate(json));

        ByteBuffer buffer = request.getData();
        System.out.println(buffer.getHexDump());
        System.out.println(request.toString());
    }
}
