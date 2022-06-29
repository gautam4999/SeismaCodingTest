package com.example.demo.entities;

public class TaxThreshold {
    private Long lowerBound;
    private Long upperBound;
    private Long flatTaxCut;
    private Double percentageCut;

    @Override
    public String toString() {
        return "TaxThreshold{" +
                "lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                ", flatTaxCut=" + flatTaxCut +
                ", percentageCut=" + percentageCut +
                '}';
    }

    public TaxThreshold() {}
    public TaxThreshold(Long lowerBound, Long upperBound, Long flatTaxCut, Double percentageCut) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.flatTaxCut = flatTaxCut;
        this.percentageCut = percentageCut;
    }

    public Long getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Long lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Long getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Long upperBound) {
        this.upperBound = upperBound;
    }

    public Long getFlatTaxCut() {
        return flatTaxCut;
    }

    public void setFlatTaxCut(Long flatTaxCut) {
        this.flatTaxCut = flatTaxCut;
    }

    public Double getPercentageCut() {
        return percentageCut;
    }

    public void setPercentageCut(Double percentageCut) {
        this.percentageCut = percentageCut;
    }
}
