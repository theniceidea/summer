package org.summerframework.rpcmodel;

public class FrameMessage {
    private byte[] meta;
    private byte[] body;

    public byte[] getMeta() {
        return meta;
    }

    public void setMeta(byte[] meta) {
        this.meta = meta;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
