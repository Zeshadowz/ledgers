package de.adorsys.ledgers.middleware.api.domain.um;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * This class composes a login process id out of the time and the userid.
 * 
 * The Formating is used for persistent data that might be valid along a multi phase login
 * process. While changes this format, care shall be taken to garanty that existing login
 * operation can be terminated. So we must always store the previous version.
 *  
 * @author fpo
 *
 */
public class LoginKeyDataTO {
	
	private static final String V_00 = "V00";
	private static final int VERSION_STRING_LENGTH = 3;
	private static final String FORMAT_V00="yyyyMMddHHmmss";
	private static final DateTimeFormatter storage_formatter = DateTimeFormatter.ofPattern(FORMAT_V00);
	private static final DateTimeFormatter display_formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
	
	private static String curent_version;
	
	private static final Map<String, String> formatMap = new HashMap<>();
	static {
		formatMap.put(V_00, FORMAT_V00);
		curent_version = V_00;
	}

    private final String userId;
    private final LocalDateTime loginTime;

	public LoginKeyDataTO(String userId, LocalDateTime time) {
		super();
		this.userId = userId;
		this.loginTime = time;
	}

	public String getUserId() {
		return userId;
	}

	public String messageTemplate() {
		if(userId==null) {
			throw new IllegalStateException("Not expecting userId to be null.");
		}

		StringBuilder b = new StringBuilder(String.format("Login process started at %s for %s:\n", loginTime.format(display_formatter), userId));

		b.append("TAN: %s");
		return b.toString();
	}
	
	public String toOpId() {
		if(curent_version.length()!=VERSION_STRING_LENGTH) {
			throw new IllegalStateException(String.format("Version string must be of length %s and shall never change.", curent_version.length()));
		}
		return curent_version + loginTime.format(storage_formatter) + userId;
	}
	
	public static LoginKeyDataTO fromOpId(String opId) {
		String version = StringUtils.substring(opId, 0, VERSION_STRING_LENGTH);
		String format = formatMap.get(version);
		if(format==null) {
			throw new IllegalStateException(String.format("Missing format with version number %s", version));
		}
		String loginDate = StringUtils.substring(opId, VERSION_STRING_LENGTH, VERSION_STRING_LENGTH+format.length());
		String userId = StringUtils.substringAfter(opId, loginDate);
		return new LoginKeyDataTO(userId, LocalDateTime.parse(loginDate, storage_formatter));
	}
}