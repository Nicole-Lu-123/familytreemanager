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
        return "?????????????????????????????????";
    }

    private String groupcalls(Integer mem1id, Integer mem2id, List<Relations> calls, Map<Integer, Member> family) {
        StringBuilder call = new StringBuilder();
        int elder = Collections.frequency(calls,Relations.MOTHER) + Collections.frequency(calls,Relations.FATHER);
        int youth = Collections.frequency(calls,Relations.SON) + Collections.frequency(calls,Relations.DAUGHTER);
        int generate = elder-youth;//????????????????????????????????????
        Relations start = (calls.get(0));
        Relations end = (calls.get(calls.size()-1));


        if (generate ==0){
            boolean older = older(mem1id,mem2id,family);
            return cousins(start,end,older);
        }else if(generate ==1){//uncle???aunt
            return uncle(start,mem1id,mem2id,family);
        }else if (generate==-1){//????????????
            return nephew(start,mem2id,family);
        }else if (generate == 2){//???????????????
            return grandparent(start,end,mem2id,family);

        }else if (generate == -2){//????????????
            return grandchild(start,end,mem2id,family);

        }else{
            int diff = Math.abs(generate) - 2;
            call.append("???".repeat(Math.max(0, diff)));
            if (generate>0){
                call.append(grandparent(start, end, mem2id, family));
            }else{
                call.append(grandchild(start, end, mem2id, family));
            }
            return call.toString();
        }
    }

    private String nephew(Relations start, Integer mem2id, Map<Integer, Member> family) {
        if (start.equals(Relations.MOTHER)){//???
            if(family.get(mem2id).memgender == Member.Gender.Male){
                return "??????";
                }else {
                return "??????";
            }
        }else{//??????
            if(family.get(mem2id).memgender == Member.Gender.Male){
                return "??????";
            }else {
                return "?????????";
            }
        }
    }

    private String uncle(Relations start, Integer mem1id, Integer mem2id, Map<Integer, Member> family) {
        int motherid = family.get(mem1id).motherid;
        int fatherid = family.get(mem1id).fatherid;
        if (start.equals(Relations.MOTHER)){//????????????
            boolean older = family.get(motherid).membirth.after(family.get(mem2id).membirth);// mother is younger
            if(family.get(mem2id).memgender == Member.Gender.Male){
                if (older){
                    return "??????";
                }else {
                    return "??????";
                }
            }else{
                if (older){
                    return "??????";
                }else {
                    return "??????";
                }
            }
        }else{
            boolean older = family.get(fatherid).membirth.after(family.get(mem2id).membirth);// father is younger
            if(family.get(mem2id).memgender == Member.Gender.Male){
                if (older){
                    return "??????";
                }else {
                    return "??????";
                }
            }else{
                if (older){
                    return "??????";
                }else {
                    return "??????";
                }
            }
        }
    }

    private String grandchild(Relations start, Relations end, Integer mem2id, Map<Integer, Member> family) {
        if (start.equals(Relations.SON)){
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "??????";
            }else{
                return "??????";
            }
        }else {
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "?????????";
            }else{
                return "??????";
            }
        }
    }

    private String grandparent(Relations start, Relations end, Integer mem2id, Map<Integer, Member> family) {
        if (start.equals(Relations.MOTHER)){
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "??????";
            }else{
                return "??????";
            }
        }else {
            if(family.get(mem2id).memgender == Member.Gender.Female){
                return "??????";
            }else{
                return "??????";
            }
        }
    }

    private String cousins(Relations start, Relations end, boolean older){
        if (start.equals(Relations.MOTHER)){
            if(end.equals(Relations.DAUGHTER)){
                if(older){
                    return "??????";
                }else{
                    return "??????";
                }
            }else{
                if(older){
                    return "??????";
                }else{
                    return"??????";
                }
            }
        }else {
            if(end.equals(Relations.DAUGHTER)){
                if (older) {
                    return  "??????";
                } else {
                    return  "??????";
                }
            } else {
                if (older) {
                    return"??????";
                } else {
                    return "??????";
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
                call = "??????";
                break;
            case FATHER:
                call = "??????";
                break;
            case SON:
                call = "??????";
                break;
            case DAUGHTER:
                call = "??????";
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
