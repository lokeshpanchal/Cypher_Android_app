package com.helio.cypher.StaticObjectDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class StataicObjectDTO
{
    public List<RiskWordDTO> getRiskWordDTOs() {
        return riskWordDTOs;
    }

    public List<SecretCategoryDTO> getSecretCategoryDTOs() {
        return secretCategoryDTOs;
    }

    public IFriendSettingDTO getiFriendSettingDTO() {
        return iFriendSettingDTO;
    }

    public String getKeywords() {
        return keywords;
    }

    List<RiskWordDTO>  riskWordDTOs ;
    List<SecretCategoryDTO>  secretCategoryDTOs ;

    public List<SchoolDTO> getSchoolDTOs() {
        return schoolDTOs;
    }

    List<SchoolDTO>  schoolDTOs ;

    IFriendSettingDTO iFriendSettingDTO = null;
    String keywords = "";
    public StataicObjectDTO( List<RiskWordDTO>  riskWordDTOs, String keywords,List<SecretCategoryDTO>  secretCategoryDTOs, IFriendSettingDTO iFriendSettingDTO
            , List<SchoolDTO> schoolDTOs)
    {
        super();
        this.riskWordDTOs = riskWordDTOs;
        this.keywords = keywords;
        this.secretCategoryDTOs = secretCategoryDTOs;
        this.iFriendSettingDTO = iFriendSettingDTO;
        this.schoolDTOs = schoolDTOs;
    }
}
