package Server.Facade;

import Server.GenericManager.Data;

public interface UIData {
    void set(Object[] uitexts);
    String[] getSimpleData();
    String[] getDetailData();
}