package nttdata.grupo.com.reportmicroservice.models.exepcion;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String DESCRIPTION = "Bad Request Exception";

    public BadRequestException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
