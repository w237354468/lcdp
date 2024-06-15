package org.lcdpframework.server.event.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.event.LcdpApplicationEvent;

import java.io.Serializable;

@Getter
public class DataSourceChangeEvent extends LcdpApplicationEvent {

    private final Change change;

    public DataSourceChangeEvent(Change change) {
        super("dataSourceChangeEvent");
        this.change = change;
    }

    public static DataSourceChangeEvent of(LcdpDataSourceDTO before, LcdpDataSourceDTO after) {
        Change changeInfo = new Change(before, after);
        return new DataSourceChangeEvent(changeInfo);
    }

    @Data
    @AllArgsConstructor
    public static class Change implements Serializable {

        private LcdpDataSourceDTO before;

        private LcdpDataSourceDTO after;
    }
}
