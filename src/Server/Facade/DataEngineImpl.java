package Server.Facade;

import java.util.ArrayList;

import Server.GenericManager.Manager;
import Server.GenericManager.Data;
import Server.ServerComputer;

public abstract class DataEngineImpl<T extends Data> extends Manager<T>
		implements IDataEngine<T> {
	String[] labels = null;

	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	@Override
	public int getColumnCount() {
		return labels.length;
	}
	// 테이블의 열 제목을 스트링 배열로 돌려줌
	public String[] getColumnNames() {
		return labels;
	}
	@Override
	public void addNewItem(String[] uiTexts) {
		// TODO Auto-generated method stub
	}
	@Override
	public ArrayList<T> search(String keyword) {
		if (keyword == null)
			return dataList;
		return findAll(keyword);
	}
	@Override
	public void update(String[] editTexts) {
		// TODO Auto-generated method stub
		Data data = find(editTexts[0]);
		((UIData)data).set(editTexts);
	}
	@Override
	public void remove(String keyword) {
		// TODO Auto-generated method stub
		Data data = find(keyword);
		dataList.remove(data);
	}
	@Override
	public UIData get(int index) {
		// TODO Auto-generated method stub
		return (UIData)dataList.get(index);
	}
	public void addElement(T data) {
		dataList.add(data);
		ServerComputer.save();
	}
}