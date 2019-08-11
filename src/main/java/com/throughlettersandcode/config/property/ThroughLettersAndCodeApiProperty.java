package com.throughlettersandcode.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("throughlettersandcode")
public class ThroughLettersAndCodeApiProperty {
	
	private String allowedOrigin = "http://localhost:4200";
	
	private final Security security = new Security();
	
	public Security getSecurity() {
		return security;
	}
	
	public String getAllowedOrigin() {
		return allowedOrigin;
	}

	public void setAllowedOrigin(String alloweString) {
		this.allowedOrigin = alloweString;
	}

	public static class Security{
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}		
	}
}