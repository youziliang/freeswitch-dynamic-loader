package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;

/**
 * @Description Kryo序列化工具
 * @Author Youziliang
 * @Date 2019/3/20
 */
public class KryoSerializer {

    // 由于kryo不是线程安全的，所以每个线程都使用独立的kryo
    private final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.register(clazz, new BeanSerializer<>(kryo, clazz));
            return kryo;
        }
    };
    private final ThreadLocal<Output> outputLocal = new ThreadLocal<>();
    private final ThreadLocal<Input> inputLocal = new ThreadLocal<>();
    private Class<?> clazz;

    public KryoSerializer(Class<?> clazz) {
        this.clazz = clazz;
    }

    public byte[] serialize(Object obj) {
        byte[] bytes = new byte[1024];
        Kryo kryo = getKryo();
        Output output = getOutput(bytes);
        kryo.writeObjectOrNull(output, obj, obj.getClass());
        output.flush();
        return bytes;
    }

    public <T> T deserialize(byte[] bytes) {
        if (null == bytes)
            return null;
        return deserialize(bytes, 0, bytes.length);
    }

    public void serialize(Object obj, byte[] bytes, int offset, int count) {
        Kryo kryo = getKryo();
        Output output = getOutput(bytes, offset, count);
        kryo.writeObjectOrNull(output, obj, obj.getClass());
        output.flush();
    }

    public <T> T deserialize(byte[] bytes, int offset, int count) {
        Kryo kryo = getKryo();
        Input input = getInput(bytes, offset, count);
        return (T) kryo.readObjectOrNull(input, clazz);
    }

    private Kryo getKryo() {
        return kryoLocal.get();
    }

    private Output getOutput(byte[] bytes) {
        Output output;
        if ((output = outputLocal.get()) == null) {
            output = new Output();
            outputLocal.set(output);
        }
        if (bytes != null) {
            output.setBuffer(bytes);
        }
        return output;
    }

    private Output getOutput(byte[] bytes, int offset, int count) {
        Output output;
        if ((output = outputLocal.get()) == null) {
            output = new Output();
            outputLocal.set(output);
        }
        if (bytes != null) {
            output.writeBytes(bytes, offset, count);
        }
        return output;
    }

    private Input getInput(byte[] bytes, int offset, int count) {
        Input input;
        if ((input = inputLocal.get()) == null) {
            input = new Input();
            inputLocal.set(input);
        }
        if (bytes != null) {
            input.setBuffer(bytes, offset, count);
        }
        return input;
    }

}
