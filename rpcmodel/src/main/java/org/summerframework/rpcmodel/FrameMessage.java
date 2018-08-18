package org.summerframework.rpcmodel;

import org.summerframework.model.SummerSum;

public class FrameMessage {
    private MetaData meta;
    private SummerSum<?> body;

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public SummerSum<?> getBody() {
        return body;
    }

    public void setBody(SummerSum<?> body) {
        this.body = body;
    }
}
