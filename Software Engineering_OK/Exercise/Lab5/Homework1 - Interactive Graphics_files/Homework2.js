"use strict";

var canvas;
var gl;
var program;

var projectionMatrix;
var modelViewMatrix;

var instanceMatrix;

var modelViewMatrixLoc;

var texture1, texture2;
var texSize = 256;
var numChecks = 8;

var texCoordsArray = [];

var texCoord = [
vec2(0, 0),
vec2(0, 1),
vec2(1, 1),
vec2(1, 0)
];

var c;
var image1 = new Uint8Array(4*texSize*texSize);
for ( var i = 0; i < texSize; i++ ) {
  for ( var j = 0; j <texSize; j++ ) {
    var patchx = Math.floor(i/(texSize/numChecks));
    var patchy = Math.floor(j/(texSize/numChecks));
    if(patchx%2 ^ patchy%2) c = 255;
    else c = 0;
    image1[4*i*texSize+4*j] = c;
    image1[4*i*texSize+4*j+1] = c;
    image1[4*i*texSize+4*j+2] = c;
    image1[4*i*texSize+4*j+3] = 255;
  }
}

var image2 = new Uint8Array(4*texSize*texSize);
for ( var i = 0; i < texSize; i++ ) {
  for ( var j = 0; j <texSize; j++ ) {
    image2[4*i*texSize+4*j] = 127+127+i;
    image2[4*i*texSize+4*j+1] = 127+127+i;
    image2[4*i*texSize+4*j+2] = 127+127+i;
    image2[4*i*texSize+4*j+3] = 255;
  }
}


function configureTexture() {
  texture1 = gl.createTexture();
  gl.bindTexture( gl.TEXTURE_2D, texture1 );
  gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true);
  gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, texSize, texSize, 0, gl.RGBA, gl.UNSIGNED_BYTE, image1);
  gl.generateMipmap( gl.TEXTURE_2D );
  gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER,
                    gl.NEAREST_MIPMAP_LINEAR );
  gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST);
  
  texture2 = gl.createTexture();
  gl.bindTexture( gl.TEXTURE_2D, texture2 );
  gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true);
  gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, texSize, texSize, 0, gl.RGBA, gl.UNSIGNED_BYTE, image2);
  gl.generateMipmap( gl.TEXTURE_2D );
  gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER,
                    gl.NEAREST_MIPMAP_LINEAR );
  gl.texParameteri( gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST);
}

var vertices = [

    vec4( -0.5, -0.5,  0.5, 1.0 ),
    vec4( -0.5,  0.5,  0.5, 1.0 ),
    vec4( 0.5,  0.5,  0.5, 1.0 ),
    vec4( 0.5, -0.5,  0.5, 1.0 ),
    vec4( -0.5, -0.5, -0.5, 1.0 ),
    vec4( -0.5,  0.5, -0.5, 1.0 ),
    vec4( 0.5,  0.5, -0.5, 1.0 ),
    vec4( 0.5, -0.5, -0.5, 1.0 )
];


var torsoId = 0;
var neckId  = 1;
var neck1Id = 1;
var neck2Id = 10;
var leftUpperArmId = 2;
var leftLowerArmId = 3;
var rightUpperArmId = 4;
var rightLowerArmId = 5;
var leftUpperLegId = 6;
var leftLowerLegId = 7;
var rightUpperLegId = 8;
var rightLowerLegId = 9;
var torsoId1 = 11;
var tailId=12;
var headId = 13;

var torsoHeight = 5.0/2;
var torsoWidth = 1.3/2;
var upperArmHeight = 3.0/2;
var lowerArmHeight = 2.0/2;
var upperArmWidth  = 0.7/2;
var lowerArmWidth  = 0.5/2;
var upperLegWidth  = 0.7/2;
var lowerLegWidth  = 0.5/2;
var lowerLegHeight = 2.0/2;
var upperLegHeight = 3.0/2;
var neckHeight = 2.5/2;
var neckWidth = 1.0/2;
var tailHeight = 2.0/2;
var tailWidth = 1.0/2;

var numNodes = 14;
var numAngles = 11;
var angle = 0;

var theta = [90, -45, 95, -2, 75, -20, 105, 20, 95, -2, 0, 90, 125, 20];
//          [0,   1,   2,  3,  4,   5,  6,    7,  8,  9,10, 11, 12, 13]

var flag = false;
var flagJump = false;
var flagTextCorso = false;
var flagTextNotCorso = false;
var flagOst = false;

var numVertices = 24;

var stack = [];

var figure = [];

for( var i=0; i<numNodes; i++) figure[i] = createNode(null, null, null, null);

var vBuffer;
var modelViewLoc;

var pointsArray = [];

//-------------------------------------------

function scale4(a, b, c) {
   var result = mat4();
   result[0][0] = a;
   result[1][1] = b;
   result[2][2] = c;
   return result;
}

//--------------------------------------------


function createNode(transform, render, sibling, child){
    var node = {
    transform: transform,
    render: render,
    sibling: sibling,
    child: child,
    }
    return node;
}

var xMovement = -9.0;
var yMovemnet = 2.5;
function initNodes(Id) {

    var m = mat4();

    switch(Id) {

    case torsoId:

    m = translate(xMovement, yMovemnet, 0);
    
    m = mult(m, rotate(theta[torsoId], 0, 1, 0 ));
    m = mult(m, rotate(theta[torsoId1], 1, 0, 0))
    figure[torsoId] = createNode( m, torso, null, neckId );
    break;

    case neckId:
    case neck1Id:
    case neck2Id:


    m = translate(0.0, torsoHeight+0.2*neckHeight, 0.0);
    m = mult(m, rotate(theta[neck1Id], 1, 0, 0))
    m = mult(m, rotate(theta[neck2Id], 0, 1, 0));
    m = mult(m, translate(0.0, -0.3*neckHeight, 0.0));
    m = mult(m, translate(0.0, 0.0, -0.2*neckHeight));
    figure[neckId] = createNode( m, neck, headId, null);
    break;

    case headId:
    m = translate(0.0, torsoHeight+0.48*neckHeight, -0.45*neckHeight);
    m = mult(m, rotate(theta[headId], 1, 0, 0));
    m = mult(m, translate(0.0, -0.3*neckHeight, 0.0));
    m = mult(m, translate(0.0, 0.0, -0.2*neckHeight));
    figure[headId] = createNode( m, head, leftUpperArmId, null);
    break;

    case leftUpperArmId:

    m = translate(-(torsoWidth-0.5+upperArmWidth), 0.9*torsoHeight, 0.0);
    m = mult(m, rotate(theta[leftUpperArmId], 1, 0, 0));
    figure[leftUpperArmId] = createNode( m, leftUpperArm, rightUpperArmId, leftLowerArmId );
    break;

    case rightUpperArmId:

    m = translate(torsoWidth-0.5+upperArmWidth, 0.9*torsoHeight, 0.0);
    m = mult(m, rotate(theta[rightUpperArmId], 1, 0, 0));
    figure[rightUpperArmId] = createNode( m, rightUpperArm, leftUpperLegId, rightLowerArmId );
    break;

    case leftUpperLegId:

    m = translate(-(torsoWidth-0.5+upperLegWidth), 0.1*upperLegHeight, 0.0);
    m = mult(m , rotate(theta[leftUpperLegId], 1, 0, 0));
    figure[leftUpperLegId] = createNode( m, leftUpperLeg, rightUpperLegId, leftLowerLegId );
    break;

    case rightUpperLegId:

    m = translate(torsoWidth-0.5+upperLegWidth, 0.1*upperLegHeight, 0.0);
    m = mult(m, rotate(theta[rightUpperLegId], 1, 0, 0));
    figure[rightUpperLegId] = createNode( m, rightUpperLeg, tailId, rightLowerLegId );
    break;

    case leftLowerArmId:

    m = translate(0.0, upperArmHeight, 0.0);
    m = mult(m, rotate(theta[leftLowerArmId], 1, 0, 0));
    figure[leftLowerArmId] = createNode( m, leftLowerArm, null, null );
    break;

    case rightLowerArmId:

    m = translate(0.0, upperArmHeight, 0.0);
    m = mult(m, rotate(theta[rightLowerArmId], 1, 0, 0));
    figure[rightLowerArmId] = createNode( m, rightLowerArm, null, null );
    break;

    case leftLowerLegId:

    m = translate(0.0, upperLegHeight, 0.0);
    m = mult(m, rotate(theta[leftLowerLegId], 1, 0, 0));
    figure[leftLowerLegId] = createNode( m, leftLowerLeg, null, null );
    break;

    case rightLowerLegId:

    m = translate(0.0, upperLegHeight, 0.0);
    m = mult(m, rotate(theta[rightLowerLegId], 1, 0, 0));
    figure[rightLowerLegId] = createNode( m, rightLowerLeg, null, null );
    break;

    case tailId:
      m = translate(0.0, -0.4*tailHeight, 0.5);
      m = mult(m, rotate(theta[tailId], 1, 0, 0))
      m = mult(m, translate(0.0, -0.6*tailHeight, 0.0));
      figure[tailId] = createNode( m, tail, null, null);
    break;
    
    }

}

function traverse(Id) {

   if(Id == null) return;
   stack.push(modelViewMatrix);
   modelViewMatrix = mult(modelViewMatrix, figure[Id].transform);
   figure[Id].render();
   if(figure[Id].child != null) traverse(figure[Id].child);
   modelViewMatrix = stack.pop();
   if(figure[Id].sibling != null) traverse(figure[Id].sibling);
}

function torso() {
    configureTexture();
    
    gl.activeTexture( gl.TEXTURE0 );
    gl.bindTexture( gl.TEXTURE_2D, texture1 );
    gl.activeTexture( gl.TEXTURE1 );
    gl.bindTexture( gl.TEXTURE_2D, texture2 );
  
    flagTextCorso = true;
    gl.uniform1i(gl.getUniformLocation( program, "flagTextCorso"), flagTextCorso);
    
    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5*torsoHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4( torsoWidth, torsoHeight, torsoWidth));
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
    
    flagTextCorso = false;
    gl.uniform1i(gl.getUniformLocation( program, "flagTextCorso"), flagTextCorso);
}

function neck() {

    flagTextNotCorso = true;
    gl.uniform1i(gl.getUniformLocation( program, "flagTextNotCorso"), flagTextNotCorso);
    
    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * neckHeight, 0.0 ));
    instanceMatrix = mult(instanceMatrix, scale4(neckWidth, neckHeight, neckWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function head() {
  
  instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * neckHeight, 0.0 ));
  instanceMatrix = mult(instanceMatrix, scale4(neckWidth, 0.7*neckHeight, neckWidth) );
  gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
  for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function leftUpperArm() {

    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * upperArmHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(upperArmWidth, upperArmHeight, upperArmWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function leftLowerArm() {

    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * lowerArmHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(lowerArmWidth, lowerArmHeight, lowerArmWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function rightUpperArm() {

    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * upperArmHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(upperArmWidth, upperArmHeight, upperArmWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function rightLowerArm() {

    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * lowerArmHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(lowerArmWidth, lowerArmHeight, lowerArmWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function  leftUpperLeg() {

    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * upperLegHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(upperLegWidth, upperLegHeight, upperLegWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function leftLowerLeg() {

    instanceMatrix = mult(modelViewMatrix, translate( 0.0, 0.5 * lowerLegHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(lowerLegWidth, lowerLegHeight, lowerLegWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function rightUpperLeg() {

    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * upperLegHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(upperLegWidth, upperLegHeight, upperLegWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function rightLowerLeg() {

    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * lowerLegHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(lowerLegWidth, lowerLegHeight, lowerLegWidth) )
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function tail() {
    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * tailHeight, 0.0 ));
    instanceMatrix = mult(instanceMatrix, scale4(tailWidth, tailHeight, tailWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
    
    flagTextNotCorso = false;
    gl.uniform1i(gl.getUniformLocation( program, "flagTextNotCorso"), flagTextNotCorso);
}

var baseId = 0;
var paloId  = 1;
var palo1Id = 2;
var astaId = 3;
var asta1Id = 4;
var palo2Id = 5;
var palo3Id = 6;

var baseHeight = 0.7/2;
var baseWidth = 2.0/2;
var paloHeight = 7.0/2;
var paloWidth  = 0.4/2;
var astaWidth  = 0.3/2;
var astaHeight = (baseWidth*5)-0.5;

var numNodesOst = 7;

var thetaOst = [-30, 0, 0, 0, 0, -83, 83];
//             [ 0,  1, 2, 3, 4  5, 6]

var stackOst = [];

var figureOst = [];

for( var i=0; i<numNodesOst; i++) figureOst[i] = createNode(null, null, null, null);

var xMovOst = 3.0;

function initNodesOst(Id) {
  
  var m = mat4();
  switch(Id) {
    
    case baseId:
      
      m = translate(xMovOst, 0, 0);
      
      m = mult(m, rotate(thetaOst[baseId], 0, 1, 0 ));
      figureOst[baseId] = createNode( m, base, null, paloId );
      break;
      
    case paloId:
      
      m = translate(0.0, baseHeight, -((baseWidth*5)/2)+paloWidth);
      figureOst[paloId] = createNode( m, palo, palo1Id, null);
      break;
    
    case palo1Id:
      m = translate(0.0, baseHeight, ((baseWidth*5)/2)-paloWidth);
      figureOst[palo1Id] = createNode( m, palo, palo2Id, null);
      break;
      
    case palo2Id:
      m = translate(0.0, baseHeight+0.1, ((baseWidth*5)/2)-paloWidth);
      m = mult(m, rotate(thetaOst[palo2Id], 1, 0, 0));
      figureOst[palo2Id] = createNode( m, paloX, palo3Id, null);
      break;  
      
    case palo3Id:
      m = translate(0.0, baseHeight+0.1, -((baseWidth*5)/2)+paloWidth);
      m = mult(m, rotate(thetaOst[palo3Id], 1, 0, 0));
      figureOst[palo3Id] = createNode( m, paloX, astaId, null);
      break;
      
    case astaId:
      m = translate(0.0, baseHeight+0.07*paloHeight, 0.0);
      figureOst[astaId] = createNode( m, asta, asta1Id, null);
      break;
      
    case asta1Id:
      m = translate(0.0, baseHeight+0.23*paloHeight, 0.0);
      figureOst[asta1Id] = createNode( m, asta1, null, null);
      break;
  }
  
}

function traverseOst(Id) {
  
    if(Id == null) return;
    stackOst.push(modelViewMatrix);
    modelViewMatrix = mult(modelViewMatrix, figureOst[Id].transform);
    figureOst[Id].render();
    if(figureOst[Id].child != null) traverseOst(figureOst[Id].child);
    modelViewMatrix = stackOst.pop();
    if(figureOst[Id].sibling != null) traverseOst(figureOst[Id].sibling);
}

function base() {
    flagOst = true;
    gl.uniform1i(gl.getUniformLocation( program, "flagOst"), flagOst);
  
    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * baseHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(baseWidth, baseHeight, baseWidth*5) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
    
    flagOst = false;
    gl.uniform1i(gl.getUniformLocation( program, "flagOst"), flagOst);
}

function palo() {
    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.5 * paloHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(paloWidth, paloHeight, paloWidth) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function paloX() {
  instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.65*paloHeight, 0.0) );
  instanceMatrix = mult(instanceMatrix, scale4(paloWidth, paloHeight+paloHeight/3, paloWidth/2) );
  gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
  for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function asta() {
    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.2 * paloHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(astaWidth, astaWidth, astaHeight) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function asta1() {
    instanceMatrix = mult(modelViewMatrix, translate(0.0, 0.2 * paloHeight, 0.0) );
    instanceMatrix = mult(instanceMatrix, scale4(astaWidth, astaWidth, astaHeight) );
    gl.uniformMatrix4fv(modelViewMatrixLoc, false, flatten(instanceMatrix));
    for(var i =0; i<6; i++) gl.drawArrays(gl.TRIANGLE_FAN, 4*i, 4);
}

function quad(a, b, c, d) {
     pointsArray.push(vertices[a]);
     pointsArray.push(vertices[b]);
     pointsArray.push(vertices[c]);
     pointsArray.push(vertices[d]);
     
     texCoordsArray.push(texCoord[0]);
     texCoordsArray.push(texCoord[1]);
     texCoordsArray.push(texCoord[2]);
     texCoordsArray.push(texCoord[3]);
}


function cube()
{
    quad( 1, 0, 3, 2 );
    quad( 2, 3, 7, 6 );
    quad( 3, 0, 4, 7 );
    quad( 6, 5, 1, 2 );
    quad( 4, 5, 6, 7 );
    quad( 5, 4, 0, 1 );
}


window.onload = function init() {

    canvas = document.getElementById( "gl-canvas" );

    gl = WebGLUtils.setupWebGL( canvas );
    if ( !gl ) { alert( "WebGL isn't available" ); }

    gl.viewport( 0, 0, canvas.width, canvas.height );
    gl.clearColor( 1.0, 1.0, 1.0, 1.0 );

    gl.enable(gl.DEPTH_TEST);
    
    //
    //  Load shaders and initialize attribute buffers
    //
    program = initShaders( gl, "vertex-shader", "fragment-shader");

    gl.useProgram( program);

    instanceMatrix = mat4();

    projectionMatrix = ortho(-10.0,10.0,-10.0, 10.0,-10.0,10.0);
    modelViewMatrix = mat4();


    gl.uniformMatrix4fv(gl.getUniformLocation( program, "modelViewMatrix"), false, flatten(modelViewMatrix) );
    gl.uniformMatrix4fv( gl.getUniformLocation( program, "projectionMatrix"), false, flatten(projectionMatrix) );

    modelViewMatrixLoc = gl.getUniformLocation(program, "modelViewMatrix")

    cube();

    vBuffer = gl.createBuffer();

    gl.bindBuffer( gl.ARRAY_BUFFER, vBuffer );
    gl.bufferData(gl.ARRAY_BUFFER, flatten(pointsArray), gl.STATIC_DRAW);

    var vPosition = gl.getAttribLocation( program, "vPosition" );
    gl.vertexAttribPointer( vPosition, 4, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vPosition );
    
    var tBuffer = gl.createBuffer();
    gl.bindBuffer( gl.ARRAY_BUFFER, tBuffer);
    gl.bufferData( gl.ARRAY_BUFFER, flatten(texCoordsArray), gl.STATIC_DRAW );
    
    var vTexCoord = gl.getAttribLocation( program, "vTexCoord" );
    gl.vertexAttribPointer( vTexCoord, 2, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vTexCoord );
    
        document.getElementById("togAnimBtn").onclick = function() {
        flag = !flag;
    };
    
        document.getElementById("slider0").onchange = function(event) {
        theta[torsoId ] = event.target.value;
        initNodes(torsoId);
    };
    
        document.getElementById("slider11").onchange = function(event) {
        thetaOst[baseId] = event.target.value;
        initNodesOst(baseId);
    };
    /*
        document.getElementById("slider1").onchange = function(event) {
        theta[neck1Id] = event.target.value;
        initNodes(neck1Id);
    };
    
    document.getElementById("slider2").onchange = function(event) {
         theta[leftUpperArmId] = event.target.value;
         initNodes(leftUpperArmId);
    };
    document.getElementById("slider3").onchange = function(event) {
         theta[leftLowerArmId] =  event.target.value;
         initNodes(leftLowerArmId);
    };

        document.getElementById("slider4").onchange = function(event) {
        theta[rightUpperArmId] = event.target.value;
        initNodes(rightUpperArmId);
    };
    document.getElementById("slider5").onchange = function(event) {
         theta[rightLowerArmId] =  event.target.value;
         initNodes(rightLowerArmId);
    };
        document.getElementById("slider6").onchange = function(event) {
        theta[leftUpperLegId] = event.target.value;
        initNodes(leftUpperLegId);
    };
    document.getElementById("slider7").onchange = function(event) {
         theta[leftLowerLegId] = event.target.value;
         initNodes(leftLowerLegId);
    };
    document.getElementById("slider8").onchange = function(event) {
         theta[rightUpperLegId] =  event.target.value;
         initNodes(rightUpperLegId);
    };
        document.getElementById("slider9").onchange = function(event) {
        theta[rightLowerLegId] = event.target.value;
        initNodes(rightLowerLegId);
    };
    document.getElementById("slider10").onchange = function(event) {
         theta[neck2Id] = event.target.value;
         initNodes(neck2Id);
    };*/

    for(i=0; i<numNodes; i++) initNodes(i);
    
    for(i=0; i<numNodesOst; i++) initNodesOst(i);
    
    gl.uniform1i(gl.getUniformLocation( program, "Tex0"), 0);
    
    gl.uniform1i(gl.getUniformLocation( program, "Tex1"), 1);
    
    render();
}

//legs
var leftLowerLegFlag = false;
var leftUpperLegFlag = false;

var rightLowerLegFlag = false;
var rightUpperLegFlag = false;

//arms
var leftLowerArmFlag = false;
var leftUpperArmFlag = false;

var rightLowerArmFlag = false;
var rightUpperArmFlag = false;

function leftLeg(){
  //left leg
  if(theta[leftLowerLegId] == -20){leftLowerLegFlag = false;}
  else if (theta[leftLowerLegId] == 20){leftLowerLegFlag = true;}
  
  if(leftLowerLegFlag) theta[leftLowerLegId]-=0.5;
  else theta[leftLowerLegId]+=0.5;
  
  if(parseInt(theta[leftUpperLegId]) == 75){leftUpperLegFlag = false;}
  else if (parseInt(theta[leftUpperLegId]) == 105){leftUpperLegFlag = true;}
  
  if(leftUpperLegFlag) theta[leftUpperLegId]-=0.4;
  else theta[leftUpperLegId]+=0.4;
}

function rightLeg(){
  //right leg
  if(theta[rightLowerLegId] == -20){rightLowerLegFlag = false;}
  else if (theta[rightLowerLegId] == 20){rightLowerLegFlag = true;}
  
  if(rightLowerLegFlag) theta[rightLowerLegId]-=0.5;
  else theta[rightLowerLegId]+=0.5;
  
  if(parseInt(theta[rightUpperLegId]) == 75){rightUpperLegFlag = false;}
  else if (parseInt(theta[rightUpperLegId]) == 105){rightUpperLegFlag = true;}
  
  if(rightUpperLegFlag) theta[rightUpperLegId]-=0.4;
  else theta[rightUpperLegId]+=0.4;
  
}

function leftArm(){
  //left arm
  if(theta[leftLowerArmId] == -20){leftLowerArmFlag = true;}
  else if (theta[leftLowerArmId] == 20){leftLowerArmFlag = false;}
  
  if(leftLowerArmFlag) theta[leftLowerArmId]+=0.5;
  else theta[leftLowerArmId]-=0.5;
  
  if(parseInt(theta[leftUpperArmId]) == 75){leftUpperArmFlag = false;}
  else if (parseInt(theta[leftUpperArmId]) == 105){leftUpperArmFlag = true;}
  
  if(leftUpperArmFlag) theta[leftUpperArmId]-=0.4;
  else theta[leftUpperArmId]+=0.4;
}

function rightArm(){
  //right arm
  if(theta[rightLowerArmId] == -20){rightLowerArmFlag = true;}
  else if (theta[rightLowerArmId] == 20){rightLowerArmFlag = false;}
  
  if(rightLowerArmFlag) theta[rightLowerArmId]+=0.5;
  else theta[rightLowerArmId]-=0.5;
  
  if(parseInt(theta[rightUpperArmId]) == 75){rightUpperArmFlag = false;}
  else if (parseInt(theta[rightUpperArmId]) == 105){rightUpperArmFlag = true;}
  
  if(rightUpperArmFlag) theta[rightUpperArmId]-=0.4;
  else theta[rightUpperArmId]+=0.4;
}

function jump(){
  var a = Math.abs(xMovement-torsoHeight);
  var b = Math.abs(xMovOst);
  var c = Math.abs(xMovement-torsoHeight+torsoHeight/2);
  var distHead = Math.abs(a-b);
  var distTail = Math.abs(c-b);
  if(xMovement < xMovOst && distHead <=1){
    theta[torsoId1]-=0.5;
    if(theta[torsoId1]<=90){
      if(theta[leftUpperArmId] >=90) theta[leftUpperArmId]-=0.5;
      else theta[leftUpperArmId]+=0.5;
      if(theta[rightUpperArmId] >=90) theta[rightUpperArmId]-=0.5;
      else theta[rightUpperArmId]+=0.5;
      if(theta[leftUpperLegId] >=90) theta[leftUpperLegId]-=0.5;
      else theta[leftUpperLegId]+=0.5;
      if(theta[rightUpperLegId] >=90) theta[rightUpperLegId]-=0.5;
      else theta[rightUpperLegId]+=0.5;
    }
    theta[headId]+=0.6;
    yMovemnet+=0.045;
    flagJump=true;
  }
  else if(xMovement >= xMovOst && distTail <=1.5){
    theta[torsoId1]+=0.6;
    theta[headId]-=0.6;
    yMovemnet-=0.023;
  }
  if(xMovement >= xMovOst && distTail >= 1 && distTail <= 1.5){
    if(theta[torsoId1]>=100){
      theta[leftUpperArmId]=95;
      theta[rightUpperArmId]=75;
      theta[leftUpperLegId]=105;
      theta[rightUpperLegId]=90;
      theta[headId]=20;
      theta[torsoId1] = 90;
      flagJump=false;
    } else theta[torsoId1]-=0.2;
    yMovemnet-=0.015;
  }
  
  /*
  if (xMovement < xMovOst && distHead <= 1 && theta[torsoId1] >= 60) {
    theta[torsoId1]-=0.5;
    theta[headId]+=0.6;
    if(distHead <= 0.5){
      theta[leftUpperLegId]=85;
      theta[rightUpperLegId]=85;
      theta[leftLowerLegId]+=1;
      theta[rightLowerLegId]+=1;
    }
    theta[leftUpperArmId]=75;
    theta[rightUpperArmId]=75;
    theta[leftLowerArmId]+=1.5;
    theta[rightLowerArmId]+=1.5;

  }
  
  if (xMovement > xMovOst && distTail <= 1 && theta[torsoId1] <= 90){
    theta[torsoId1]+=0.5;
    theta[headId]-=0.6;
    if(theta[torsoId1]==90){
      theta[leftUpperArmId]=95;
      theta[rightUpperArmId]=75;
      theta[leftUpperLegId]=105;
      theta[rightUpperLegId]=90;
      theta[headId]=20;
    }
    theta[leftLowerArmId]=-2;
    theta[rightLowerArmId]=-20;
    theta[leftLowerLegId]=20;
    theta[rightLowerLegId]=-2;

  }
  */
}

var render = function() {

        if(flag){
          if(!flagJump){
            leftLeg();
            rightLeg();
            leftArm();
            rightArm();
          }
          jump();
          if (xMovement > 10.0) {
            xMovement=-10.0;
            theta[torsoId1] =90;
            yMovemnet=2.5;
          }
          xMovement += 0.05;
          for(i=0; i<numNodes; i++) initNodes(i);;
        }
        gl.clear( gl.COLOR_BUFFER_BIT );
        traverse(torsoId);
        traverseOst(baseId);

        requestAnimFrame(render);
}
