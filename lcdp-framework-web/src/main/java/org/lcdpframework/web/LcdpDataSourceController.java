package org.lcdpframework.web;

import jakarta.validation.Valid;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.dto.TableDetailInfo;
import org.lcdpframework.server.impl.manager.LcdpDataSourceService;
import org.lcdpframework.web.copier.LcdpDataSourceWebCopier;
import org.lcdpframework.web.model.Response;
import org.lcdpframework.web.model.qo.LcdpDataSourceAdd;
import org.lcdpframework.web.model.qo.LcdpDataSourceQuery;
import org.lcdpframework.web.model.vo.LcdpDataSourceResult;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/datasources")
public class LcdpDataSourceController {

    private final LcdpDataSourceWebCopier dataSourceWebCopier;
    private final LcdpDataSourceService dataSourceService;

    public LcdpDataSourceController(LcdpDataSourceWebCopier dataSourceWebCopier,
                                    LcdpDataSourceService dataSourceService) {
        this.dataSourceWebCopier = dataSourceWebCopier;
        this.dataSourceService = dataSourceService;
    }

    @PostMapping
    public Response<String> add(@RequestBody @Valid LcdpDataSourceAdd dataSourceAdd) {
        String dataSourceId = dataSourceService.add(dataSourceWebCopier.addToDTO(dataSourceAdd));
        return Response.ok(dataSourceId);
    }

    @GetMapping("/{id}")
    public Response<LcdpDataSourceResult> getById(@PathVariable("id") String dataSourceId) {
        LcdpDataSourceDTO lcdpDataSourceDTO = dataSourceService.getById(dataSourceId);
        return Response.ok(dataSourceWebCopier.dtoToResult(lcdpDataSourceDTO));
    }

    @GetMapping("/")
    public Response<Page<LcdpDataSourceResult>> getList(
            @RequestParam(required = false, defaultValue = "") String dataModelName,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        LcdpDataSourceQuery dataSourceQuery = new LcdpDataSourceQuery(dataModelName, pageSize, pageNum);
        Page<LcdpDataSourceDTO> pageResult = dataSourceService.getList(
                dataSourceWebCopier.queryToDTO(dataSourceQuery));
        return Response.ok(dataSourceWebCopier.dtoPageToResultPage(pageResult));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delById(@PathVariable("id") String dataSourceId) {
        dataSourceService.delete(dataSourceId);
        return Response.ok();
    }

    @PutMapping("/{id}")
    public Response<Void> update(@PathVariable("id") String dataSourceId,
                                 @RequestBody @Valid LcdpDataSourceAdd update) {
        dataSourceService.update(dataSourceId, dataSourceWebCopier.addToDTO(update));
        return Response.ok();
    }

    /**
     * test whether connection can be established by given properties
     */
    @GetMapping("/{id}/test/connect")
    public Response<String> testConnect(@PathVariable("id") String dataSourceId) {
        String result = dataSourceService.testConnect(dataSourceId);
        return Response.ok(result);
    }

    /**
     * get all tables and its column infos under the datasource by given properties
     */
    @GetMapping("/{id}/tables")
    public Response<List<TableDetailInfo>> connectSingle(@PathVariable("id") String dataSourceId) {
        return Response.ok(dataSourceService.connect(dataSourceId));
    }

    @PostMapping("/{id}/table/columns")
    public Response<List<DataModelColumnsInfoDTO>> queryAttr(
            @PathVariable("id") String dataSourceId,
            @RequestBody Map.Entry<String, List<String>> tableNames) {
        return Response.ok(dataSourceService.queryColumns(dataSourceId, tableNames.getValue()));
    }
}
