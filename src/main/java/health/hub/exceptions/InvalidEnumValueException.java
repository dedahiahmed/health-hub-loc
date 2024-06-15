package health.hub.exceptions;

public class InvalidEnumValueException extends IllegalArgumentException {

    public InvalidEnumValueException(String enumType, String value) {
        super("Invalid value '" + value + "' provided for enum type: " + enumType);
    }
}
