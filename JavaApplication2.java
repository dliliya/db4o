package javaapplication2;
import com.db4o.*;
import java.util.ArrayList;
import java.util.List;
import com.db4o.query.Constraint;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class JavaApplication2 {

    public static void main(String[] args) {
        // accessDb4o
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded
        .newConfiguration(), "D:\\mydb");
        try {
            fill_base(db);            
            printActors(db);
            printFilms(db);
            retrieveByComparison(db); //Выведем все фильмы, год которых > 2005
            retrieveComplexSODA(db);   //Выведем актеров мужского пола возрастом от 40 до 50
            retrieveSorted(db);  //Выведем фильмы отсортированные по наименованию
            retrieveArbitraryCodeNQ(db); //Выведем фильмы 2000,2009,2012 с режисером, имя, которого начинается с  Guy
            getFilmsForActor(db); //Выведем все фильмы, в которых снимались актеры, имеющие оскар
            getFilmsFromCountry(db); //Выведем все фильмы, в которых актеров из США не меньше 2
            funk1(db); //Выведем всех актеров, снявшихся в фильмах Гая Ричи
            clearDatabase(db);
        } finally {
            db.close();
        }
    }
    
    public static void funk1(ObjectContainer db) {
        //Выведем всех актеров, снявшихся в фильмах Гая Ричи
        Query query = db.query();
        query.constrain(Actor.class);
        Query filmsquery = query.descend("films");
        filmsquery.constrain(Film.class);
        Query valuequery = filmsquery.descend("director");
        valuequery.constrain("Guy Ritchie");
        ObjectSet result = query.execute();
        listResult(result);
        
    }
    
    public static void getFilmsFromCountry(ObjectContainer db) {
        //Выведем все фильмы, в которых актеров из США не меньше 2
    	List<Film> results = db.query(new Predicate<Film>() {
            public boolean match(Film candidate) {                
                List<Actor> Actors = candidate.getActors();
                if (Actors.size()<2) return false;
                int cnt = 0;
                for (Actor actor : Actors) {
                    if(actor.getCountry().contains("USA")){
                        cnt+=1;
                        if (cnt >= 2)
                            return true;
                    }                           
                }
                return false;
            }
        });
        listResult(results);
    }
    
    public static void getFilmsForActor(ObjectContainer db){
        //Выведем все фильмы, в которых снимались актеры, имеющие оскар
        Query query = db.query();
        query.constrain(Film.class);
        Query actorsquery = query.descend("Actors");
        actorsquery.constrain(Actor.class);
        Query valuequery = actorsquery.descend("has_oskar");
        valuequery.constrain(true);
        ObjectSet result = query.execute();
        listResult(result);
        
    }
    
    public static void printActors(ObjectContainer db){
        Actor proto = new Actor();
        ObjectSet result = db.queryByExample(proto);
        listResult(result);
    }
    
    public static void printFilms(ObjectContainer db){
        Film proto = new Film();
        
        List<Film> result = db.queryByExample(proto);
        result.forEach((Film o) -> {
            System.out.println(o.toString());
            List<Actor> Actors = o.getActors();
            System.out.print("    Actors{");
            for (Actor actor : Actors){
                 System.out.print(""+actor.getName()+",");
            }
            System.out.print("}");
            System.out.println();
        });
        
    }
            
    public static void listResult(List<?> result){
    	//System.out.println(result.size());
        result.forEach((o) -> {
            System.out.println(o);
        });
    }
    
    public static void fill_base(ObjectContainer db){   
           
            //films
            Film film1=new Film("The Avengers","Joss Whedon", "USA",2012);
            db.store(film1);
            Film film2=new Film("Sherlock Holmes","Guy Ritchie", "USA",2009);
            db.store(film2);
            Film film3=new Film("Snatch","Guy Ritchie", "USA",2000);
            db.store(film3);
            Film film4=new Film("Lock, Stock and Two Smoking Barrels","Guy Ritchie", "UK",1998);
            db.store(film4);
            Film film5=new Film("Shutter Island","Martin Scorsese", "USA",2009);
            db.store(film5);
            
            ArrayList<Film> films1 = new ArrayList<>();
            films1.add(film1);
            films1.add(film2);
            
            ArrayList<Film> films2 = new ArrayList<>();
            films2.add(film3);
            
            ArrayList<Film> films3 = new ArrayList<>();
            films3.add(film3);
            films3.add(film4);
            
            ArrayList<Film> films4 = new ArrayList<>();
            films4.add(film5);
            
            //actors
            Actor actor1=new Actor(films1, "Robert Downey Jr.", "M", "USA", "New York",52,79,false);
            db.store(actor1);
            Actor actor2=new Actor(films2, "Brad Pitt", "M", "USA", "New York",53,66,true);
            db.store(actor2);
            Actor actor3=new Actor(films3, "Jason Statham", "M", "UK", "Derbyshire",49,38,false);
            db.store(actor3);
            Actor actor4=new Actor(films4, "Leonardo DiCaprio", "M", "USA", "Hollywood",42,37,true);
            db.store(actor4);
            
            ArrayList<Actor> actors1 = new ArrayList<>(); 
            actors1.add(actor1); //дауни
            film1.setActors(actors1);
            db.store(film1);//мстители
            
            ArrayList<Actor> actors2 = new ArrayList<>();
            actors2.add(actor1);// дауни
            actors2.add(actor4); // лео
            film2.setActors(actors2);
            db.store(film2);  //шерлок
            
            ArrayList<Actor> actors3= new ArrayList<>();
            actors3.add(actor2); //питт
            actors3.add(actor3); // стэтхэм
            actors3.add(actor1); // 
             actors3.add(actor4); // лео
            film3.setActors(actors3);
            db.store(film3);//большой куш
            
            ArrayList<Actor> actors4= new ArrayList<>();
            actors4.add(actor3); // стэтхэм
            film4.setActors(actors4);
            db.store(film4);//карты деньги
            
            ArrayList<Actor> actors5= new ArrayList<>();
            actors5.add(actor4); // лео
            actors5.add(actor2); //питт
            film5.setActors(actors5);
            db.store(film5);//остров проклятых
            
    }

    public static void clearDatabase(ObjectContainer db) {
        ObjectSet result=db.queryByExample(Film.class);
        while(result.hasNext()) {
            db.delete(result.next());
        }
        ObjectSet result1=db.queryByExample(Actor.class);
        while(result1.hasNext()) {
            db.delete(result1.next());
        }
    }
    
    public static void retrieveByComparison(ObjectContainer db) {
        //Выведем все фильмы, год которых > 2005
        Query query=db.query();
        query.constrain(Film.class);
        query.descend("year")
                .constrain(2005).greater();
        ObjectSet result=query.execute();
        listResult(result);
    }
    
    public static void retrieveSorted(ObjectContainer db) {
        //Выведем фильмы отсортированные по наименованию
        Query query=db.query();
        query.constrain(Film.class);
        query.descend("name").orderAscending();
        ObjectSet result=query.execute();
        listResult(result);
        query.descend("name").orderDescending();
        result=query.execute();
        //listResult(result);
    }

    public static void retrieveComplexSODA(ObjectContainer db) {
        //Выведем актеров мужского пола возрастом от 40 до 50
        Query query=db.query();
        query.constrain(Actor.class);
        Query pointQuery=query.descend("age");
        query.descend("sex").constrain("M")
        	.and(pointQuery.constrain(40).greater()
        	    .and(pointQuery.constrain(50).smaller()));
        ObjectSet result=query.execute();
        listResult(result);
    }
    
    public static void retrieveArbitraryCodeNQ(ObjectContainer db) {
        //Выведем фильмы 2000,2009,2012 с режисером, имя, которого начинается с  Guy
    	final int[] years={2000,2009,2012};
        List<Film> result=db.query(new Predicate<Film>() {
        	public boolean match(Film film) {
                    for (int year : years ){
                        if (film.getYear() == year && film.getDirector().startsWith("Guy")) {
                            
                            return true;
                        }
                    }
                    return false;
                }
        });
        listResult(result);
    }
}
