// Data e hora exibido no sistema
function ano(a) {
	$(a).jclock({format: '%Y'});
}

function dataHora() {
    $('#relogio').jclock({format: '%d/%m/%Y %H:%M'});
}

function boasVindasHoras() {
    $('.boas-vindas-horas').jclock({format: '%H:%M'});
}

function boasVindasData() {
    $('.boas-vindas-data').jclock({format: '%a, %d de %B'});
}

/* ***** */

function apenasNumeros() {
	$('.numeros').keyup(function(e) {
		var valorTemp = $(this).val();

		if (/\D/g.test(valorTemp)) {
			// Substitui caracteres que nao sejam numeros por ''
			$(this).val(valorTemp.replace(/\D/g, ''));
		}
	});
}

function maskCEP() {
	$('.cep').mask('00000-000');
}

function maskCNPJ() {
	$('.cnpj').mask('00.000.000/0000-00');
}

function maskCPF() {
	$('.cpf').mask('000.000.000-00');
}

function maskData() {
	$('.input-datepicker').mask('00/00/0000');
	$('.input-datepicker').mask("00/00/0000", {placeholder: '__/__/____'});
}

function maskDataHora() {
	$('.input-datahora').mask('00/00/0000 00:00');
	$('.input-datahora').mask("00/00/0000 00:00", {placeholder: '__/__/____ __:__'});
}

function maskDocumento() {
	var masks = [ '000.000.000-009', '00.000.000/0000-00' ];

	var maskBehavior = function(val) {
		return val.replace(/\D/g, '').length <= 11 ? masks[0] : masks[1];
	}, options = {
		onKeyPress : function(val, e, field, options) {
			field.mask(val.replace(/\D/g, '').length <= 11 ? masks[0]
					: masks[1], options);
			
			console.log('mask');
		}
	};

	$('.documento').mask(maskBehavior, options);
}

function maskMoeda() {
	$('.moeda').mask('000.000.000.000.000,00', {reverse: true});
	// $('.moeda').mask('#.##0,00', {reverse: true});
}

function maskTelefone() {
	var masks = ['00 00000-0000', '00 0000-00009'];

	$('.telefone').mask(masks[1], {
		onKeyPress : function(val, e, field, options) {
			field.mask(val.length > 14 ? masks[0] : masks[1], options);
		}
	});
}

function multiSelect() {
	$('select[multiple]').multipleSelect({
		placeholder: 'Selecione',
		selectAllText: 'SELECIONAR TODOS',
		allSelected: 'TODOS SELECIONADOS',
		countSelected: 'FORAM SELECIONADOS # DE %',
		noMatchesFound: 'NÃO HÁ REGISTRO(S)',
		maxHeight: 120,
		filter: true
	});
}

/* ***** */

function mostraPreloader() {
	$('#preloader').show();
}

function escondePreloader() {
	$('#preloader').hide();
}

/* ***** */

function bsModal(a, b, c, d, e) {
	var acao;

	// a = Elemento
	// b = Tipo da acao
	// c = Nome da Tab
	// d = Numero da Tab (index 0)
	// e = Titulo da Modal

	if (b === 'mostra') {
		acao = 'show';
	} else if (b === 'esconde') {
		acao = 'hide';
	}
	
	$(a).modal(acao);
	
	if (typeof e !== 'undefined') {
		$(a + ' .modal-title').text(e);
	}
	
	$(a).on('shown.bs.modal', function() {
		if ((typeof c !== 'undefined') && (typeof d !== 'undefined')) {
			$(c + ' li:eq(' + d + ') a').tab('show');
		}
	});
}

function carregaJS() {
	ano('.ano-sistema'); // Ano exibido no rodape do sistema
	
	dataHora(); boasVindasHoras(); boasVindasData();
	apenasNumeros();
	maskCEP();
	maskCNPJ(); maskCPF(); 
	maskData();
	maskDataHora();
	maskDocumento();
	maskMoeda();
	maskTelefone();
	multiSelect();
}

$(document).ready(function(){
	carregaJS();
});

