package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

class Graph {


    private List<Vetrex> graph;

    public Graph() {
        graph = new ArrayList<Vetrex>();

    }

    public Graph(Graph g) {
        graph = new ArrayList<>(g.getGraph());
//        Vetrex temp;
//        for (Vetrex v: g.getGraph()){
//            temp= new Vetrex(v);
//            graph.add(temp);
//            for(Vetrex n: v.neg){
//                temp= new Vetrex(n);
//                v.addEdge(temp);
//            }
//        }

    }
    public List<Vetrex> getGraph(){
        return graph;
    }

    public void add_vetrex(Vetrex v) {
        if (!this.contains_vetrex(v))
            graph.add(v);
    }

    public boolean contains_vetrex(Vetrex v) {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).equals(v))
                return true;
        }
        return false;
    }
    public int get_vetrex_index(Vetrex v){
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).equals(v))
                return i;
        }
        return -1;
    }
    public void addEdge(Vetrex shift_reg, Vetrex worker, Vetrex shift_wanted){
        add_vetrex(shift_reg);
        add_vetrex(worker);
        add_vetrex(shift_wanted);

       int worker_index=get_vetrex_index(worker);
       int shift_reg_index=get_vetrex_index(shift_reg);

       graph.get(shift_reg_index).addEdge(worker);
       graph.get(worker_index).addEdge(shift_wanted);
    }
    public void printGraph(){
        for (Vetrex v: graph){
            System.out.print(v.id+" : ");
            for (Vetrex u: v.neg)
                System.out.println(u.id+" , ");
            System.out.println();
        }
    }
}
class Vetrex{

    String id;
    boolean is_user;
    LinkedList<Vetrex> neg;

    public Vetrex(boolean is_user, String id){
        neg= new LinkedList<>();
        this.is_user=is_user;
        this.id=id;
    }

    public Vetrex(Vetrex v){
        neg= new LinkedList<>(v.neg);
        this.is_user=v.is_user;
        this.id=id;
    }

    public void addEdge(Vetrex v){
        if (!contains_edge(v))
            this.neg.add(v);
    }

    public boolean contains_edge(Vetrex v){
        for (int i=0; i<this.neg.size(); i++)
            if (neg.get(i).equals(v))
                return true;
        return false;
    }
    public  LinkedList<Vetrex> getNeg(){
        return neg;
    }

    public boolean equals(Vetrex v){
        return  this.id.equals(v.id);
    }
}
