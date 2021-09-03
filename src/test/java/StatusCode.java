public enum StatusCode {
    okCode (200),
    created (201),
    notFound (404);
    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getCode(){
        return value;
    }
}
