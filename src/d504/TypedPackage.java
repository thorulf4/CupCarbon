package d504;

public class TypedPackage {

    public PackageType type;
    public String packageData;

    public TypedPackage(PackageType type, String packageData){
        this.type = type;
        this.packageData = packageData;
    }

    public String serialize(){
        return type.ordinal() + "&" +packageData;
    }

    public static TypedPackage deserialize(String data){
        int firstSeperatorIndex = data.indexOf("&");

        String typeString = data.substring(0, firstSeperatorIndex);
        PackageType type = convertToType(typeString);

        String packageData = data.substring(firstSeperatorIndex + 1);

        return new TypedPackage(type, packageData);
    }

    private static PackageType convertToType(String typeString) {
        int typeOrdinal = Integer.parseInt(typeString);
        return PackageType.values()[typeOrdinal];
    }

}

