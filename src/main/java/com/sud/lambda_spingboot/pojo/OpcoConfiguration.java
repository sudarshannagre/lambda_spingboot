package com.sud.lambda_spingboot.pojo;

import lombok.Data;

@Data //is equivalent to combination of Lombok’s @Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
public class OpcoConfiguration {

	String partnerId;
	String appId;
	String appToken;
	String opcoId;
	String opcoName;
	String opcoDescription;
	String opcoLanguage;
	String opcoDomain;
	String domain;
	String opcoCountryName;
	String linearAssetType;
	String networkParameter;
	
}
