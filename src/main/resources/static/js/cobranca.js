//PesquisaTitulos
$('#confirmacaoExclusaoModal')
	.on('show.bs.modal',
		function(event) {
			var button = $(event.relatedTarget);

			// codigo vem da tag link atributo
			// th:attr="data-codigo=${titulo.codigo}"
			var codigoTitulo = button.data('codigo');
			var descricaoTitulo = button.data('descricao');

			var modal = $(this);

			var form = modal.find('form');

			// pega a string que esta em action "/titulos/numero"
			//var action = form.attr('action');
			var action = form.data('url-base');

			if (!action.endsWith('/')) {
				action += '/';
			}

			form.attr('action', action + codigoTitulo);

			modal.find('.modal-body span').html(
				'Tem certeza que deseja excluir o titulo <strong>'
				+ descricaoTitulo + '<strong>?');

		});

//CadastroTitulo
$(function() {
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({decimal: ',', thousands: '.', allowZero: true});
	$('.js-atualizar-status').on('click', function(event){
		event.preventDefault();//nao fazer a funcao do link de direcionar
		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');
		//console.log(urlReceber);
		//ajax requisicao put na url
		var response = $.ajax({
			url: urlReceber, 
			type: 'PUT'
			
		});
	});
});