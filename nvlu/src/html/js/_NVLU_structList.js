function _NVLU_structList(){ 
  this.List = new Array();
 
  this.size = function(){
 	 return this.List.length;
  }
 
  this.add = function(node){
 		this.List[this.List.length] = node;
  }
	
  this.get = function(i){
 		if(i>=this.List.length||i<0){
 			return null;
 		}
 		return this.List[i];
  }
	
	this.remove = function(node){
		for(var i=0;i<this.List.length;i++){
		   if(this.List[i].pk == node.pk){
         if(i<this.List.length-1 && i>0){
				   var tmpNode = this.List[i];
				   this.List[i] = this.List[this.List.length-1];
				   this.List[this.List.length-1] = tmpNode;
				   this.List.pop();
				 }else if(i==0){
           this.List.shift();
				 }else if(i==this.List.length-1){
           this.List.pop();
				 }
         break;
			 }
		}
	}
}