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
});