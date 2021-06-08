package org.serratec.com.backend.ecommerce.enums;

public enum PurchasesStatus {
	FINALIZADO(1, "Pedido finalizado com sucesso!"), NAO_FINALIZADO(0, "Aguardando confirmação do pedido");

	private final Integer status;
	private final String descricao;

	private PurchasesStatus(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDescricao() {
		return descricao;
	}

}
