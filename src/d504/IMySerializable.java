package d504;

public interface IMySerializable<T> {
    public String Serialize(T object);
    public T Deserialize();
}
