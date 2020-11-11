package com.company;

import java.util.ArrayList;

public class UtilAnimal {

    public Noise makeNoise(Animal a, int index){

        return a.getNoises(index);
    }

    public static void learnNoise(Animal a, String n){
        a.addNoise(new Noise(n));
    }

    public static void addNoise(Animal a, Noise n){
        a.noises.add(n);
    }

    public static void sayHi(Animal a, Animal b){
        System.out.println(a.noises.get(0).n);
        System.out.println(b.noises.get(0).n);
    }
}
    And the following classes:

public class Noise {
    public String n;
    public Noise(String n){
        this.n=n;
    }
}

import java.util.ArrayList;
public class Animal {
    private String type;
    private int numLegs;
    private ArrayList<Noise> noises;

    public Animal(String type, int numLegs){
        this.type=type; this.numLegs=numLegs;
        noises = new ArrayList<Noise>();
    }

    public int getNumLegs() {
        return numLegs;
    }
    public void setNumLegs(int numb) {
        this.numLegs = numb;
    }

    public Noise getNoises(int index) {
        return noises.get(index);
    }

    public void addNoise(Noise noise) {
        noises.add(noise);
    }
}


public class AnimalZoo {
    public static void main(String[] args){
        Animal dog = new Animal("dog", 4);
        Animal duck = new
                Animal("duck", 2);

        UtilAnimal.learnNoise(dog,"woof woof");
        UtilAnimal.learnNoise(dog,"grrr");
        UtilAnimal.learnNoise(duck,"quack");

        UtilAnimal.sayHi(dog, duck);

        System.out.println(UtilAnimal.(dog,1).n);
        duck.setNumLegs(1);
    }
}
