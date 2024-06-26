package org.lcdpframework.server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdpframework.dao.dataobject.JoinInfo;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpDataModelDTO extends PageQuery implements Serializable {
    private String id;
    private String dataModelName;
    private String describe;
    private String dataSourceId;
    private LcdpDataSourceDTO dataSource;
    private List<JoinInfo> joinInfos;
    private List<DataModelColumnsInfoDTO> dataModelColumns;
}
