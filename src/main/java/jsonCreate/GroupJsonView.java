package jsonCreate;
public class GroupJsonView {
	int 	group_id;
	String ac_title,group_name,	ac_type;
	public GroupJsonView()
	{
	}
	public GroupJsonView(int group_id, String ac_title, String group_name, String ac_type) {
		super();
		this.group_id = group_id;
		this.ac_title = ac_title;
		this.group_name = group_name;
		this.ac_type = ac_type;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getAc_title() {
		return ac_title;
	}
	public void setAc_title(String ac_title) {
		this.ac_title = ac_title;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String toString() {
        return "[" + group_id +" "+ ac_title + " "+ group_name +" "+ ac_type+" ]";
	}
}
