package libs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Member {
    Integer memid, fatherid, motherid;
    String memname;
    Gender memgender;
    Date membirth;

    Relation rl = new Relation();


    enum Gender {Male, Female}

    private static Map<Integer, Member> family = new HashMap<>();

    public Member() {

    }

    public Member(Integer id, String name, String gender, Date birth, Integer fatherid, Integer motherid) {
        this.memid = id;
        this.memname = name;
        this.memgender = transgender(gender);
        this.membirth = birth;
        this.fatherid = fatherid;
        this.motherid = motherid;

    }

    private Gender transgender(String gender) {
        if (gender.equals("男")) {
            return Gender.Male;
        } else {
            return Gender.Female;
        }
    }

    public boolean checkmem(int mem1id, String mem1name) {
        return family.get(mem1id).memname.equals(mem1name);
    }

    public void addmember(Integer id, String name, String gender, Date birth, Integer fatherid, Integer motherid) {
        Member mem = new Member(id, name, gender, birth, fatherid, motherid);
        family.put(id, mem);
        rl.addmem(id, mem);
    }

    public boolean checkid(int memid) {
        return family.containsKey(memid);
    }

    public void deleteMem(int memid) {
        rl.deletemem(memid, family.get(memid));
        family.remove(memid);
    }

    public void updateMem(int memid, String memname, String memgender, Date membirth, int memfather, int memmother) {
        // id is unchangeable
        Member oldmem = family.get(memid);
        Member newmem = new Member(memid, memname, memgender, membirth, memfather, memmother);
        family.replace(memid, newmem);
        rl.updatemem(memid, newmem, oldmem);
    }

    public void addexamples() {
        addmember(1, "王大锤", "男", new Date(19900101), 3, 4);
        addmember(2, "王尼美", "女", new Date(19900201), 3, 4);
        addmember(3, "王建国", "男", new Date(19700303), 0, 0);
        addmember(4, "李秀英", "女", new Date(19700303), 0, 0);
        addmember(5, "赵铁柱", "男", new Date(19990404), 0, 2);
        addmember(6, "王小明", "男", new Date(19990505), 1, 0);
    }

    public int older(int mem1id, int mem2id) {
        if (family.get(mem1id).motherid == family.get(mem2id).motherid && family.get(mem1id).fatherid == family.get(mem2id).fatherid) {
            if (family.get(mem1id).membirth.after(family.get(mem2id).membirth)) {
                return mem2id;
            } else {
                return mem1id;
            }
        }else{
            return 0;
        }
    }

    public Gender getgender(int id) {
        return family.get(id).memgender;
    }

    public int wife(int mem1id, int mem2id) {
        for (Integer i1 : family.keySet()) {
            int father = family.get(i1).fatherid;
            int mother = family.get(i1).motherid;
            if (father != 0 && mother != 0) {
                if (mem1id == father && mem2id == mother) {
                    return mem2id;
                } else if (mem1id == mother && mem2id == father) {
                    return mem1id;
                }
            }
        }
        return 0;
    }
    public String findcall(int mem1id, int mem2id) {
        if (mem1id == mem2id) {
            return "自己";
        } else if (older(mem1id, mem2id) == mem1id) { //bro or sister
            if (getgender(mem2id) == Member.Gender.Male) {
                return "弟弟";
            } else {
                return "妹妹";
            }
        } else if (older(mem1id, mem2id) == mem2id) { //bro or sister
            if (getgender(mem2id) == Member.Gender.Male) {
                return "哥哥";
            } else {
                return "姐姐";
            }
        } else if (wife(mem1id, mem2id) == mem1id) {
            return "老公";
        } else if (wife(mem1id, mem2id) == mem2id) {
            return "老婆";
        } else {
            return rl.parsetocall(mem1id,mem2id,family);
        }
    }
}
