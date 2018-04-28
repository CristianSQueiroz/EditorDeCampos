/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordecampos;

/**
 *
 * @author Cristian
 */
public class CampoDinamico {

    private int id;
    private String label;
    private String tipo;
    private String tipoValor;
    private boolean isEditavel;
    private int posX;
    private int posY;
    private int altura;
    private int comprimento;
    private String dsDescricao;
    private String dsSQL;
    private String dsAtributo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isIsEditavel() {
        return isEditavel;
    }

    public void setIsEditavel(boolean isEditavel) {
        this.isEditavel = isEditavel;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }

    public String getDsDescricao() {
        return dsDescricao;
    }

    public void setDsDescricao(String dsDescricao) {
        this.dsDescricao = dsDescricao;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public String getDsSQL() {
        return dsSQL;
    }

    public void setDsSQL(String dsSQL) {
        this.dsSQL = dsSQL;
    }

    public String getDsAtributo() {
        return dsAtributo;
    }

    public void setDsAtributo(String dsAtributo) {
        this.dsAtributo = dsAtributo;
    }

    
}
