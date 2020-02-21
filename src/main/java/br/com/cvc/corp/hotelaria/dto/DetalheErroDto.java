package br.com.cvc.corp.hotelaria.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("detalheErro")
public class DetalheErroDto extends BaseDto {

    private static final long serialVersionUID = -2243742480047312690L;

    private Integer codigo;
    private String mensagem;

    public DetalheErroDto() {
        super();
    }

    public DetalheErroDto(final Integer codigo, final String mensagem) {
        super();
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public DetalheErroDto(final String mensagem) {
        super();
        this.mensagem = mensagem;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(final Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(final String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "DetalheErroDto [codigo=" + codigo + ", mensagem=" + mensagem + "]";
    }

}
