package io.zestic.activemq.test;

import io.zestic.core.entity.Auditable;
import io.zestic.core.entity.Model;
import lombok.Data;

@Data
public class Sample extends Model<Integer, Sample> implements Auditable {

    private Integer id;
    private Integer collectionId;
    private Integer productId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
