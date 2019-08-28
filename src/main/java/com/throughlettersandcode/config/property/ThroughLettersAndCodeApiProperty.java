package com.throughlettersandcode.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("throughlettersandcode")
public class ThroughLettersAndCodeApiProperty {
	
	private String[] allowedOrigins = {"http://localhost:4201"};
	
	private final Security security = new Security();
	
	public Security getSecurity() {
		return security;
	}
	
	public String[] getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(String[] origins) {
		this.allowedOrigins = origins;
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