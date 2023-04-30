/*const getAcc = document.getElementById('getAcc');
const wrapper = document.querySelector('.wrapper');
const main = document.querySelector('.main');
const signUpLink = document.querySelector('.sign_Up');
const logInLink = document.querySelector('.log_In');
const closeReg = document.querySelector('.icon-close');

logInLink.addEventListener('click',()=>{
	wrapper.classList.add('active_login');
	wrapper.classList.remove('active');
	wrapper.style.transform = 'scale(1.0)';
	main.style.opacity="60%";
})

signUpLink.addEventListener('click',()=>{
	wrapper.classList.remove('active_login');
	wrapper.style.transform = 'scale(1.0)';
	wrapper.classList.add('active');
	main.style.opacity="60%";
})

getAcc.addEventListener('click',()=>{
	wrapper.classList.remove('active_login');
	wrapper.style.transform = 'scale(1.0)';
	wrapper.classList.add('active');
	main.style.opacity="60%";
})

closeReg.addEventListener('click',()=>{
	wrapper.classList.remove('active');
	wrapper.classList.remove('active_login');
	wrapper.style.transform = 'scale(0)';
	main.style.opacity="100%";
})*/

const main = document.querySelector('.main');
const container = document.querySelector('.container.registr');
const login = document.querySelector('.log_In')
const signup = document.querySelector('.sign_Up');
const btn = document.getElementById('btn');
const getAcc = document.querySelector('.buttongetAcc.getAcc');
const forgot = document.querySelector('.forgot');
const contin = document.querySelector('.continue');

document.addEventListener('click',(e)=>{
	const click = e.composedPath().includes(container);
	const click1 = e.composedPath().includes(login);
	const click2 = e.composedPath().includes(signup);
	const click3 = e.composedPath().includes(getAcc);
	const click4 = e.composedPath().includes(forgot);
	const click5 = e.composedPath().includes(contin);
	if(!click&&!click1&&!click2&&!click3&&!click4&&!click5){
		container.style.visibility = 'hidden';
		main.style.opacity = "100%";
		btn.style.transition = '.0s';
	}
})

var x = document.getElementById("login");
var y = document.getElementById("signup");
var z = document.getElementById("btn");
var f = document.getElementById("forgot");
var fo = document.getElementById('forgotOtp');
var c = document.getElementById('changePass');
var gc = document.getElementById('goodchange');

function logIn(){
	z.style.left = "50%";
	x.style.bottom = "-1000px"
	x.style.position = 'static'
	y.style.position = 'absolute'
	y.style.bottom = "1000px";
	btn.style.transition = '.5s';
	main.style.opacity = "60%";
	f.style.bottom = "1000px";
	f.style.position = 'absolute';
	fo.style.bottom = '1000px';
	fo.style.position = 'absolute';
	c.style.bottom = '1000px';
	c.style.position = 'absolute';
	gc.style.bottom = '1000px';
	gc.style.position = 'absolute';
}

function signUp(){
	z.style.left = "0";
	x.style.bottom = "1000px"
	x.style.position = 'absolute'
	y.style.position = 'static'
	y.style.bottom = "-1000px"
	btn.style.transition = '.5s';
	main.style.opacity = "60%";
	f.style.bottom = "1000px";
	f.style.position = 'absolute';
	fo.style.bottom = '1000px';
	fo.style.position = 'absolute';
	c.style.bottom = '1000px';
	c.style.position = 'absolute';
	gc.style.bottom = '1000px';
	gc.style.position = 'absolute';
}


login.addEventListener('click',()=>{
	container.style.visibility='visible';
	z.style.left = "50%";
	x.style.bottom = "-1000px"
	x.style.position = 'static'
	y.style.position = 'absolute'
	y.style.bottom = "1000px"
	btn.style.transition = '.5s';
	main.style.opacity = "60%";
	f.style.bottom = "1000px";
	f.style.position = 'absolute';
	fo.style.bottom = '1000px';
	fo.style.position = 'absolute';
	c.style.bottom = '1000px';
	c.style.position = 'absolute';
	gc.style.bottom = '1000px';
	gc.style.position = 'absolute';
})

signup.addEventListener('click',()=>{
	container.style.visibility='visible';
	z.style.left = "0";
	x.style.bottom = "1000px"
	x.style.position = 'absolute'
	y.style.position = 'static'
	y.style.bottom = "-1000px"
	btn.style.transition = '.5s';
	main.style.opacity = "60%";
	f.style.bottom = "1000px";
	f.style.position = 'absolute';
	fo.style.bottom = '1000px';
	fo.style.position = 'absolute';
	c.style.bottom = '1000px';
	c.style.position = 'absolute';
	gc.style.bottom = '1000px';
	gc.style.position = 'absolute';
})

getAcc.addEventListener('click',()=>{
	container.style.visibility='visible';
	z.style.left = "0";
	x.style.bottom = "1000px"
	x.style.position = 'absolute'
	y.style.position = 'static'
	y.style.bottom = "-1000px"
	btn.style.transition = '.5s';
	main.style.opacity = "60%";
	f.style.bottom = "1000px";
	f.style.position = 'absolute';
	fo.style.bottom = '1000px';
	fo.style.position = 'absolute';
	c.style.bottom = '1000px';
	c.style.position = 'absolute';
	gc.style.bottom = '1000px';
	gc.style.position = 'absolute';
})

forgot.addEventListener('click',()=>{
	container.style.visibility='visible';
	z.style.left = "50%";
	x.style.bottom = "1000px"
	x.style.position = 'absolute'
	y.style.position = 'absolute'
	y.style.bottom = "1000px";
	btn.style.transition = '.5s';
	main.style.opacity = "60%";
	f.style.bottom = "-1000px";
	f.style.position = 'static';
	fo.style.bottom = '1000px';
	fo.style.position = 'absolute';
	c.style.bottom = '1000px';
	c.style.position = 'absolute';
	gc.style.bottom = '1000px';
	gc.style.position = 'absolute';
})

contin.addEventListener('click',()=>{
	container.style.visibility='visible';
	z.style.left = "50%";
	x.style.bottom = "-1000px"
	x.style.position = 'static'
	y.style.position = 'absolute'
	y.style.bottom = "1000px"
	btn.style.transition = '.5s';
	main.style.opacity = "60%";
	f.style.bottom = "1000px";
	f.style.position = 'absolute';
	fo.style.bottom = '1000px';
	fo.style.position = 'absolute';
	c.style.bottom = '1000px';
	c.style.position = 'absolute';
	gc.style.bottom = '1000px';
	gc.style.position = 'absolute';
})




