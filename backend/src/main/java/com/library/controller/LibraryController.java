package com.library.controller;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class LibraryController {
    private final List<Map<String,Object>> books = new ArrayList<>();
    private final List<Map<String,Object>> members = new ArrayList<>();
    private final List<Map<String,Object>> issues = new ArrayList<>();
    private final List<Map<String,Object>> reservations = new ArrayList<>();
    private final List<Map<String,Object>> notices = new ArrayList<>();
    private final AtomicLong bookId = new AtomicLong(6);
    private final AtomicLong issueId = new AtomicLong(1);
    private final AtomicLong reservationId = new AtomicLong(1);
    private final AtomicLong noticeId = new AtomicLong(4);

    public LibraryController() {
        books.add(book(1,"9780133970777","Fundamentals of Database Systems","Ramez Elmasri","Database",5,5,"A-01"));
        books.add(book(2,"9780073523323","Operating System Concepts","Abraham Silberschatz","Operating Systems",4,4,"A-02"));
        books.add(book(3,"9780132350884","Clean Code","Robert C. Martin","Software Engineering",5,5,"B-01"));
        books.add(book(4,"9780134685991","Effective Java","James Gosling","Programming",3,3,"B-02"));
        books.add(book(5,"9780133591620","Modern Operating Systems","Andrew S. Tanenbaum","Operating Systems",4,4,"A-03"));
        members.add(member(1,"Akshara Student","student@library.com","CSE","B.Tech",3,"ACTIVE"));
        members.add(member(2,"Rahul Kumar","rahul@example.com","CSE","B.Tech",2,"ACTIVE"));
        members.add(member(3,"Priya Sharma","priya@example.com","ECE","B.Tech",3,"ACTIVE"));
        members.add(member(4,"Arjun Reddy","arjun@example.com","IT","B.Tech",4,"ACTIVE"));
        members.add(member(5,"Sneha Rao","sneha@example.com","CSE","B.Tech",1,"ACTIVE"));
        notices.add(notice(1,"Library Hours","Library is open from 8 AM to 8 PM during examination week.","HIGH"));
        notices.add(notice(2,"New Books","New programming and database books are now available.","NORMAL"));
        notices.add(notice(3,"Return Reminder","Please return overdue books as soon as possible.","HIGH"));
    }

    private Map<String,Object> book(long id,String isbn,String title,String author,String category,int total,int available,String shelf) {
        Map<String,Object> b=new LinkedHashMap<>();
        b.put("id",id); b.put("isbn",isbn); b.put("title",title); b.put("author",author); b.put("category",category);
        b.put("totalCopies",total); b.put("availableCopies",available); b.put("shelfNumber",shelf); return b;
    }
    private Map<String,Object> member(long id,String name,String email,String dept,String course,int year,String status) {
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("id",id); m.put("name",name); m.put("email",email); m.put("department",dept); m.put("course",course); m.put("year",year); m.put("status",status); return m;
    }
    private Map<String,Object> notice(long id,String title,String desc,String priority) {
        Map<String,Object> n=new LinkedHashMap<>();
        n.put("id",id); n.put("title",title); n.put("description",desc); n.put("date",LocalDate.now().toString()); n.put("priority",priority); return n;
    }

    @GetMapping("/books")
    public List<Map<String,Object>> books(){ return books; }

    @PostMapping("/books")
    public Map<String,Object> addBook(@RequestBody Map<String,Object> b){
        long id=bookId.getAndIncrement(); b.put("id",id);
        if(!b.containsKey("availableCopies")) b.put("availableCopies",b.getOrDefault("totalCopies",1));
        books.add(b); return b;
    }

    @PutMapping("/books/{id}")
    public Map<String,Object> updateBook(@PathVariable long id,@RequestBody Map<String,Object> body){
        return books.stream().filter(x -> ((Number)x.get("id")).longValue()==id).findFirst().map(x->{x.putAll(body);x.put("id",id);return x;}).orElseThrow();
    }

    @DeleteMapping("/books/{id}")
    public Map<String,Object> deleteBook(@PathVariable long id){
        books.removeIf(x -> ((Number)x.get("id")).longValue()==id);
        return Map.of("success",true);
    }

    @GetMapping("/members")
    public List<Map<String,Object>> members(){ return members; }

    @GetMapping("/issues")
    public List<Map<String,Object>> issues(){ return issues; }

    @PostMapping("/issues")
    public Map<String,Object> issue(@RequestBody Map<String,Object> body){
        long bid=((Number)body.get("bookId")).longValue();
        Map<String,Object> b=books.stream().filter(x->((Number)x.get("id")).longValue()==bid).findFirst().orElseThrow();
        int available=((Number)b.get("availableCopies")).intValue();
        if(available<=0) throw new IllegalStateException("Book is not available");
        b.put("availableCopies",available-1);
        body.put("id",issueId.getAndIncrement());
        body.put("issueDate",LocalDate.now().toString());
        body.putIfAbsent("status","ISSUED");
        issues.add(body);
        return body;
    }

    @PutMapping("/issues/{id}/return")
    public Map<String,Object> returnBook(@PathVariable long id){
        Map<String,Object> issue=issues.stream().filter(x->((Number)x.get("id")).longValue()==id).findFirst().orElseThrow();
        if("RETURNED".equals(issue.get("status"))) return issue;
        long bid=((Number)issue.get("bookId")).longValue();
        books.stream().filter(x->((Number)x.get("id")).longValue()==bid).findFirst().ifPresent(b->b.put("availableCopies",((Number)b.get("availableCopies")).intValue()+1));
        issue.put("status","RETURNED"); issue.put("returnDate",LocalDate.now().toString());
        return issue;
    }

    @GetMapping("/reservations")
    public List<Map<String,Object>> reservations(){ return reservations; }

    @PostMapping("/reservations")
    public Map<String,Object> reserve(@RequestBody Map<String,Object> body){
        body.put("id",reservationId.getAndIncrement());
        body.put("reservationDate",LocalDate.now().toString());
        body.putIfAbsent("status","PENDING");
        reservations.add(body); return body;
    }

    @GetMapping("/notices")
    public List<Map<String,Object>> notices(){ return notices; }

    @PostMapping("/notices")
    public Map<String,Object> addNotice(@RequestBody Map<String,Object> body){
        body.put("id",noticeId.getAndIncrement());
        body.put("date",LocalDate.now().toString());
        notices.add(body); return body;
    }

    @GetMapping("/dashboard/stats")
    public Map<String,Object> stats(){
        long available=books.stream().mapToInt(x->((Number)x.get("availableCopies")).intValue()).sum();
        int total=books.stream().mapToInt(x->((Number)x.get("totalCopies")).intValue()).sum();
        return Map.of("totalBooks",total,"availableBooks",available,"issuedBooks",total-available,
                "members",members.size(),"overdueBooks",0,"pendingFines",0,"reservations",reservations.size());
    }
}
