package top.dooper.top.exception;

public class ServerErrorException extends Exception {
    private final String message = "Wrong management API address!";
    public ServerErrorException() throws Exception {
        throw new Exception(message);
    }
}
