$(document).ready(function(){

	$.getScript("/js/functions.js");

	$("#dolgozoFeltoltesForm").submit(function(e){
		e.preventDefault();
		if($("#DolgJelszo").val() != $("#DolgJelszoUjra").val()){
			$("#jelszoUjraText").addClass("text-danger").text("Nem egyezik a jelszó!");
		}else{
			$("#jelszoUjraText").removeClass("text-danger").text("");
			Feltoltes({nev: $("#DolgNev").val(), email: $("#DolgEmail").val(), masodlagosEmail: $("#MasodlagosDolgEmail").val(), oraber: $("#DolgOraber").val(), szorzo: $("#DolgSzorzo").val(), jelszo: $("#DolgJelszo").val(), ujrajelszo: $("#DolgJelszoUjra").val(), tipus: 'dolgozo'});
		}
		
	});
	
	$("#munidoDolgozok").change(function(){
		$.ajax({
			url:"/admin/dolgozo/"+$("#munidoDolgozok").val(),
			type:"GET",
			dataType:"json",
			success: function(succ){
				$("#munidoProjektek option").remove();
				$(succ.userpro).each(function(i){
					$("<option value='"+succ.userpro[i].projekt.id+"'>"+succ.userpro[i].projekt.name+"</option>").appendTo("#munidoProjektek");
				});
			},
			error: function(err){
				
			}
		});
	});
	
	$("#munkaidoForm").submit(function(e){
		e.preventDefault();
		let raforditas = ($("#munidoOra").val()/10)*10 + ($("#munidoPerc").val()/60);
		raforditas = Math.round((raforditas + Number.EPSILON) * 100) / 100;

		Feltoltes({dolgozo: $("#munidoDolgozok").val(), projekt: $("#munidoProjektek").val(), raforditas: raforditas, muntipus: $("#munidoTipus").val(), leiras: $("#munidoLeiras").val(), datum: $("#munidoDate").val(), tipus: "munka"});
	});
	
	$("#dolgozoModositasForm").submit(function(e){
		
		e.preventDefault();
		$("#biztosText").text("Biztos módosít?");
		biztosModal.toggle();
		$("#igenBiztos").click(function(){
			Modositas({id: $("#hiddenId").val(), nev: $("#ModDolgNev").val(), oraber: $("#ModDolgOraber").val(), szorzo: $("#ModDolgSzorzo").val(), tipus: "dolgozo"});
			biztosModal.hide();
			modositasModal.hide();
			location.reload();
		});		
		
		
	});
	
	$("input[id='checkie']").click(function(){
		if($(this).prop('checked') == true){
			Aktivitas({id: $(this).attr('value'), aktiv: 1, tipus: 'dolgozo'});
		}else if($(this).prop('checked') == false){
			Aktivitas({id: $(this).attr('value'), aktiv: 0, tipus: 'dolgozo'});
		}
	});
	
	
	$(".modosit").click(function(){
		$.ajax({
			url:"/admin/dolgozo/"+$(this).attr('id'),
			type:"GET",
			dataType:"json",
			success: function(succ){
				//console.log(JSON.stringify(succ));
				$("#ModDolgNev").val(succ.teljesnev);
				$("#ModDolgOraber").val(succ.alaporaber);
				$("#ModDolgSzorzo").val(succ.bruttoszorzo);
				$("#hiddenId").val(succ.id);
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
		});
	});
	
	$("#dolgozok").change(function(){

		KoteshezProjektek({id: $(this).val(), tipus: "dolgozo"});

	});
	
	$("#kotesDolgozoForm").submit(function(e){
		
		e.preventDefault();
		
		KotesFeltoltes({dolgozo: $("#dolgozok").val(), projekt: $("#projektek").val(), oraber: $("#oraber").val(), tipus: "dolgozo"});
		
	});
	
	$(".kotesTorles").click(function(){
		let id = $(this).attr('id');
		$("#biztosText").text("Biztos törli?");
		biztosModal.toggle();
		$("#igenBiztos").click(function(){
			KotesTorles({id: id, tipus: "dolgozo"});
			biztosModal.hide();
			//location.reload();
		});
		
	});
	
});