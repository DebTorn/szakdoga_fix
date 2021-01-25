$(document).ready(function(){
	
	$.getScript("/js/functions.js");
	
	$("#ugyfelek").change(function(){
	
		$("#projektek option").remove();
		
		KoteshezProjektek({id: $(this).val(), tipus: "ugyfel"});

	});
	
	$(".modosit").click(function(){
		let id = $(this).attr('id');
			$.ajax({
				url:'/admin/ugyfel/'+id,
				type:'GET',
				dataType:'json',
				success: function(succ){
					$(".nevMod").val(succ.name);
					$(".nevMod").attr('id', succ.id);
				},
				error: function(err){
					console.log(JSON.stringify(err));
				}
			});		
	});
	
	$(".kotesTorles").click(function(){
			let id = $(this).attr('id');
			$("#biztosText").text("Biztos törli?");
			biztosModal.toggle();
			$("#igenBiztos").click(function(){
				KotesTorles({id: id, tipus: "ugyfel"});
				biztosModal.hide();
				location.reload();
			});
		
	});
	
	
	
	$("#ugyfelModositasForm").submit(function(e){
		
		e.preventDefault();
				$("#biztosText").text("Biztos módosít?");
				biztosModal.toggle();
				$("#igenBiztos").click(function(){
					Modositas({id: $(".nevMod").attr('id'), nev: $(".nevMod").val(), tipus: "ugyfel"});
					biztosModal.hide();
					modositasModal.hide();
					location.reload();
				});
		
	});
	
	$("#ujUgyfelForm").submit(function(e){
		
		e.preventDefault();
		
		Feltoltes({nev: $("#ujNev").val(), tipus: 'ugyfel'});
		
	});
	
	$("input[id='checkie']").click(function(){
		if($(this).prop('checked') == true){
		
			Aktivitas({id: $(this).attr('value'), aktiv: 1, tipus: 'ugyfel'});

		}else if($(this).prop('checked') == false){
		
			Aktivitas({id: $(this).attr('value'), aktiv: 0, tipus: 'ugyfel'});

		}
	});
	
	$("#kotesUgyfelForm").submit(function(e){
		
		e.preventDefault();
		
		KotesFeltoltes({ugyfel: $("#ugyfelek").val(), projekt: $("#projektek").val(), oraber: $("#oraber").val(), tipus: "ugyfel"});
		
	});
	
});