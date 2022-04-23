package com.human.pojo.statistics;

/**
 * @author Li JieGui
 * @date 2021/4/5 22:41
 */
public class StatisticalVisit {
    private String name;
    private int visit;
    private int resume;
    private Double absolutely;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public int getResume() {
        return resume;
    }

    public void setResume(int resume) {
        this.resume = resume;
    }

    public Double getAbsolutely() {
        return absolutely;
    }

    public void setAbsolutely(Double absolutely) {
        this.absolutely = absolutely;
    }

    @Override
    public String toString() {
        return "StatisticalVisit{" +
                "name='" + name + '\'' +
                ", visit=" + visit +
                ", resume=" + resume +
                ", absolutely=" + absolutely +
                '}';
    }
}
