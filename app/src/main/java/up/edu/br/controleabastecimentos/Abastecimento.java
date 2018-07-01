package up.edu.br.controleabastecimentos;

import java.io.Serializable;
import java.util.Date;

public class Abastecimento implements Serializable{

    private Integer id;
    private String data;
    private Double custoTotal;
    private Double precoLitro;
    private Double litros;
    private int odometro;
    private int tanqueCheio;
    private Double media;
    private String posto;

    @Override
    public boolean equals(Object o){
        if (id == null || ((Abastecimento)o).getId() == null){
            return false;
        }
        return id.equals(((Abastecimento)o).getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(Double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public Double getPrecoLitro() {
        return precoLitro;
    }

    public void setPrecoLitro(Double precoLitro) {
        this.precoLitro = precoLitro;
    }

    public Double getLitros() {
        return litros;
    }

    public void setLitros(Double litros) {
        this.litros = litros;
    }

    public int getOdometro() {
        return odometro;
    }

    public void setOdometro(int odometro) {
        this.odometro = odometro;
    }

    public int getTanqueCheio() {
        return tanqueCheio;
    }

    public void setTanqueCheio(int tanqueCheio) {
        this.tanqueCheio = tanqueCheio;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }
}
