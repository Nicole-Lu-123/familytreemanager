package libs;

import java.util.*;

public class Relation {


    enum Relations {MOTHER, FATHER, SON, DAUGHTER}
    ArrayList<Relations> result;
    private static final Map<Integer,HashMap<Integer, Relations>> familyMap = new HashMap<>();
    public Relation() {

    }

    public void addRelation(Integer i1, Relations rel1, Integer i2, Relations rel2, int mode){

        HashMap<Integer, Relations> personalMap = new HashMap<>();

        if (familyMap.get(i1) == null){
            personalMap.put(i2,rel1);
            familyMap.put(i1, personalMap);
        } else {
            personalMap = familyMap.get(i1);
            personalMap.putIfAbsent(i2, rel1);
        }
        if (mode == 0){
            addRelation(i2, rel2, i1, rel1, 1);
        }
    }

    public ArrayList<Relations> findrelations(Map<Integer,HashMap<Integer, Relations>> map,int mem1id, int mem2id) {
        HashMap<Integer, Relations> relationships = map.get(mem1id);
        if (relationships.containsKey(mem2id)){
            result.add(relationships.get(mem2id));
            return result;
        }else {
            for (Integer key:relationships.keySet()) {
//                HashMap<Integer, Relations> furtherrelations = familyMap.get(key);
                Map<Integer,HashMap<Integer, Relations>> newmap = cleanmap(map,mem1id,key);
                if (findrelations(newmap,key,mem2id).size() != 0){
                    result.add(relationships.get(key));
                    return result;
                }

            }
            return result;
        }
    }
    public Map<Integer,HashMap<Integer, Relations>> cleanmap(Map<Integer, HashMap<Integer, Relations>> map, int mem1id, Integer key){
        map.get(key).remove(mem1id);
        return map;
    }
    public String parsetocall(Integer mem1id, Integer mem2id, Map<Integer, Member> family) {
        result = new ArrayList<>();
        List<Relations> result1 = findrelations(familyMap,mem1id, mem2id);
        Collections.reverse(result1);
        if (result1.size() == 1){
            return transcall(result1.get(0));
        }
        if (result1.size() > 1) {
            return groupcalls(mem1id,mem2id,result1,family);
        }
        return "没有称呼（两人无关系）";
    }

    private String groupcalls(Integer mem1id, Integer mem2id, List<Relations> calls, Map<Integer, Member> family) {
        StringBuilder call = new StringBuilder();
        int elder = Collections.frequency(calls,Relations.MOTHER) + Collections.frequency(calls,Relations.FATHER);
        int youth = Collections.frequency(calls,Relations.SON) + Collections.frequency(calls,Relations.DAUGHTER);
        int generate = elder-youth;//先不考虑丈人，亲家什么的
        Relations start = (calls.get(0));
        Relations end = (calls.get(calls.size()-1));


        if (generate ==0){
            boolean older = older(mem1id,mem2id,family);
            return cousins(start,end,older);
        }else if(generate ==1){//uncle，aunt
            return uncle(start,mem1id,mem2id,family);
        }else if (generate==-1){//侄，外甥
            return nephew(start,mem2id,family);
        }else if (generate == 2){//爷爷，姥爷
            return grandparent(start,end,mem2id,family);

        }else if (generate == -2){//孙，外孙
            return grandchild(start,end,mem2id,family);

        }else{
            int diff = Math.abs(generate) - 2;
            call.append("曾".repeat(Math.max(0, diff)));
            if (generate>0){
                call.append(grandparent(start, end, mem2id, family));
            }else{
                call.append(grandchild(start, end, mem2id, family));
            }
            return call.toString();
        }
    }

    private String nephew(Relations start, Integer mem2id, Map<Integer, Member> family) {
        if (start.equals(Relations.MOTHER)){//侄
            if(family.get(mem2id).memgender == Member.Gender.Male){
                return "侄子";
                }else {
                return "侄女";
            }
        }else{//外甥
            if(family.get(mem2id).memgender == Member.Gender.Male){
                return "外甥";
            }else {
                return "外甥女";
            }
        }
    }

    private String uncle(Relations start, Integer mem1id, Integer mem2id, Map<Integer, Member> family) {
        int motherid = family.get(mem1id).motherid;
        int fatherid = family.get(mem1id).fatherid;
        if (start.equals(Relations.MOTHER)){//舅舅姨妈
            boolean older = family.get(motherid).membirth.after(family.get(mem2id).membirth);// mother is younger
            if(family.get(mem2id).memgender == Member.Gender.Male){
                if (older){
                    return "大舅";
                }else {
                    return "小舅";
                }
            }else{
                if (older){
                    return "大姨";
                }else {
                    return "小姨";
                }
            }
        }else{
            boolean older = family.get(fatherid).membirth.after(family.get(mem2id).membirth);// father is younger
            if(family.get(mem2id).memgender == Member.Gender.Male){
                if (older){
                    return "伯伯";
                }else {
                    return "叔叔";
                }
            }else{
                if (older){
                    return "大姑";
                }else {
                    return "小姑";
                }
            }
        }
    }

    private String grandchild(Relations start, Relations end, Integer mem2id, Map<Integer, Member> family) {
        if (start.equals(Relations.SON)){
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "孙女";
            }else{
                return "孙子";
            }
        }else {
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "外孙女";
            }else{
                return "外孙";
            }
        }
    }

    private String grandparent(Relations start, Relations end, Integer mem2id, Map<Integer, Member> family) {
        if (start.equals(Relations.MOTHER)){
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "姥姥";
            }else{
                return "姥爷";
            }
        }else {
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "奶奶";
            }else{
                return "爷爷";
            }
        }
    }

    private String cousins(Relations start, Relations end, boolean older){
        if (start.equals(Relations.MOTHER)){
            if(end.equals(Relations.DAUGHTER)){
                if(older){
                    return "堂姐";
                }else{
                    return "堂妹";
                }
            }else{
                if(older){
                    return "堂哥";
                }else{
                    return"堂弟";
                }
            }
        }else {
            if(end.equals(Relations.DAUGHTER)){
                if (older) {
                    return  "表姐";
                } else {
                    return  "表妹";
                }
            } else {
                if (older) {
                    return"表哥";
                } else {
                    return "表弟";
                }
            }
        }
    }
    private boolean older(Integer mem1id, Integer mem2id, Map<Integer, Member> family){
        return family.get(mem1id).membirth.after(family.get(mem2id).membirth);
    }
    private String transcall(Relations relations) {
        String call;
        call = "";
        switch (relations){
            case MOTHER:
                call = "妈妈";
                break;
            case FATHER:
                call = "爸爸";
                break;
            case SON:
                call = "儿子";
                break;
            case DAUGHTER:
                call = "女儿";
                break;
        }
        return call;
    }

    void updatemem(Integer id, Member newmem, Member oldmem) {
        deletemem(id,oldmem);
        addmem(id,newmem);
    }

    void deletemem(Integer id, Member mem) {
        Integer self = id;
        Integer father = mem.fatherid;
        Integer mother = mem.motherid;
        familyMap.get(father).remove(id);
        familyMap.get(mother).remove(id);
        familyMap.remove(self);
    }

    void addmem(Integer id, Member mem){
        Integer self = id;
        Integer father = mem.fatherid;
        Integer mother = mem.motherid;
        Member.Gender selfgender = mem.memgender;
        Date birthday = mem.membirth;
        if (selfgender == Member.Gender.Male){
            if (mother != 0) {
                addRelation(self, Relations.MOTHER, mother, Relations.SON, 0);
            }
            if (father != 0) {
                addRelation(self, Relations.FATHER, father, Relations.SON, 0);
            }
        } else if(selfgender == Member.Gender.Female){
            if (mother != 0) {
                addRelation(self, Relations.MOTHER, mother, Relations.DAUGHTER, 0);
            }
            if (father != 0) {
                addRelation(self, Relations.FATHER, father, Relations.DAUGHTER, 0);
            }
        }
    }
}
