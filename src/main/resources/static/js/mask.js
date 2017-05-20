//http://igorescobar.github.io/jQuery-Mask-Plugin/
$(document).ready(function(){
	  $('.js-data').mask('00/00/0000', {placeholder: "__/__/____"});
	  $('.js-dinheiro').mask('000.000.000.000.000,00', {reverse: true});

	  $('.js-data-limparSeNaoAtender').mask("00/00/0000", {clearIfNotMatch: true});
	  $('.js-data-selecionaAoFocar').mask("00/00/0000", {selectOnFocus: true});
	  
	  $('.js-time').mask('00:00:00');
	  $('.js-data_tempo').mask('00/00/0000 00:00:00');
	  $('.js-cep').mask('00000-000');
	  $('.js-phone').mask('0000-0000');
	  $('.js-celular').mask('(00) Z0000-0000', {
		    translation: {
		      'Z': {
		        pattern: /[0-9]/, optional: true
		      }
		    }
		  });
	  
	  
	  $('.js-phone_with_ddd').mask('(00) 0000-0000');
	  $('.js-phone_us').mask('(000) 000-0000');
	  $('.js-mixed').mask('AAA 000-S0S');
	  $('.js-cpf').mask('000.000.000-00', {reverse: true});
	  $('.js-cnpj').mask('00.000.000/0000-00', {reverse: true});
	  $('.js-money2').mask("#.##0,00", {reverse: true});
	  $('.js-ip_address').mask('0ZZ.0ZZ.0ZZ.0ZZ', {
	    translation: {
	      'Z': {
	        pattern: /[0-9]/, optional: true
	      }
	    }
	  });
	  $('.js-ip_address').mask('099.099.099.099');
	  $('.js-percent').mask('##0,00%', {reverse: true});
	  
	  $('.js-placeholder').mask("00/00/0000", {placeholder: "__/__/____"});
	  $('.js-fallback').mask("00r00r0000", {
	      translation: {
	        'r': {
	          pattern: /[\/]/,
	          fallback: '/'
	        },
	        placeholder: "__/__/____"
	      }
	    });
	});