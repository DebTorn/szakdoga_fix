let biztosModal = new bootstrap.Modal(document.getElementById('biztos'));
let modositasModal = new bootstrap.Modal(document.getElementById('modositasModal'));

function Feltoltes(json){
	$.ajax({
			url:"/admin/feltoltes",
			type:"POST",
			dataType:"json",
			data:json,
			success: function(succ){
				console.log(JSON.stringify(succ));
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
	});
}

function DolgozoFeltoltes(json){
	$.ajax({
			url:"/dolgozo/feltoltes",
			type:"POST",
			dataType:"json",
			data:json,
			success: function(succ){
				console.log(JSON.stringify(succ));
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
	});
}

function Modositas(json){
	$.ajax({
			url:"/admin/modositas",
			type:"POST",
			dataType:"json",
			data:json,
			success: function(succ){
				console.log(JSON.stringify(succ));
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
	});
}

function Torles(json){
	$.ajax({
		url:"/admin/torles",
		type:"POST",
		dataType:"json",
		data:json,
		success: function(succ){
			console.log(JSON.stringify(succ));
		},
		error: function(err){
			console.log(JSON.stringify(err));
		}
	});
}

function KotesTorles(json){
	$.ajax({
			url:'/admin/kotes/torles',
			type:'POST',
			dataType:'json',
			data:json,
			success: function(succ){
				console.log(JSON.stringify(succ));
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
	});
}

function KotesFeltoltes(json){
	$.ajax({
			url:"/admin/kotes/feltoltes",
			type:"POST",
			dataType:"json",
			data:json,
			success: function(succ){
				console.log(JSON.stringify(succ));
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
	});
}

function Aktivitas(json){
	$.ajax({
			url:"/admin/aktivitas",
			type:"POST",
			dataType:"json",
			data:json,
			success: function(succ){
				console.log(JSON.stringify(succ));
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
	});
}

function KoteshezProjektek(json){

		$("#projektek option").remove();

		$.ajax({
			url:"/admin/kotes/projektek",
			type:"POST",
			dataType:'json',
			data: json,
			success: function(succ){
				$(succ).each(function(i){
					$("<option value="+succ[i].id+">"+succ[i].name+"</option>").appendTo("#projektek");
				});
			},
			error: function(err){
				console.log(JSON.stringify(err));
			}
		});
}