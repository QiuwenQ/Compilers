	.data
	.align 2
_a:	.space 4	# Global var a
	.text
	.globl main
main:	
__start:		# add __start label for main only
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 12		#set the FP for this function
	subu  $sp, $sp, 12		#push space for locals
	li    $t0, 4		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, _a		#get address global var a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	li    $t0, 1		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	mult  $t0, $t1		#operation *
	mflo  $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	add   $t0, $t0, $t1		#operation +
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -12($fp)	#get address of localm12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	la    $t0, -12($fp)	#get address of localm12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 0($t0)	#put value into T1
	move  $a0, $t1		#put value to A0
	li    $v0, 1
	syscall
	lw    $t0, _a		#get global var a
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	div   $t0, $t1		#operation /
	mflo  $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 3		#load intlit into TO
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	mult  $t0, $t1		#operation *
	mflo  $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	la    $t0, -12($fp)	#get address of localm12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($t1)	#ASSIGN
	la    $t0, -12($fp)	#get address of localm12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 0($t0)	#put value into T1
	move  $a0, $t1		#put value to A0
	li    $v0, 1
	syscall
_main_Exit:
	lw    $ra, -4($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -8($fp)	#restore FP
	move  $sp, $t0		#restore SP
	li    $v0, 10		#load exit code for syscall
	syscall		#only do this for main
