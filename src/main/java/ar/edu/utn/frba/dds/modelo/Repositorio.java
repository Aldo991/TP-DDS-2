package ar.edu.utn.frba.dds.modelo;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class Repositorio<T> implements WithSimplePersistenceUnit {

  public static Repositorio instancia;
  //private List<T> elementos = new ArrayList<>();

  public Repositorio() {
  }

  //----SINGLETON----??

  //--DAO--

  private Class claseContenida(){
    return (Class) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  public T buscar(Long id){
    /*T resultado = elementos.stream().filter(p->p.getId().equals(id)).findFirst().orElse(null);

    if(resultado==null){
      resultado = (T) entityManager().find(claseContenida(), id);
    }*/
    return (T) entityManager().find(claseContenida(), id);
  }

  public void registrar(T t){
    //this.elementos.add(t);
    entityManager().persist(t);
  }

  public void remover (T t){
    T elem = entityManager().merge(t);
    //this.elementos.remove(t);
    entityManager().remove(elem);
  }

  public List<T> todos(){
    List<T> elementos = entityManager().createQuery("FROM "+claseContenida().getSimpleName()).getResultList();
    return elementos;
  }

  //ContieneA(T t)


}
