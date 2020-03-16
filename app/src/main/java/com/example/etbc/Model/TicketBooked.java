package com.example.etbc.Model;


public class TicketBooked {
    public TicketBooked() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public TicketBooked(String id, String source, String dest, int count, int amt, String start, String end, String is_retn, String retn, String uid,String Email,String uname) {
        this.id = id;
        this.source = source;
        this.dest = dest;
        this.count = count;
        this.amt = amt;
        this.start = start;
        this.end = end;
        this.is_retn = is_retn;
        this.retn = retn;
        this.uid=uid;
        this.email = Email;
        this.uname=uname;
    }

    String id;
    String source;
    String dest;
    int count;
    int amt;
    String start;
    String end;
    String is_retn;
    String retn;
    String uid;
    private String email;
    String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    String uname;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getIs_retn() {
        return is_retn;
    }

    public void setIs_retn(String is_retn) {
        this.is_retn = is_retn;
    }

    public String getRetn() {
        return retn;
    }

    public void setRetn(String retn) {
        this.retn = retn;
    }
}
