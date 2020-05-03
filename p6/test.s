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
	li    $t0, 2		#load intlit into TO
	sw    $t0, 0($sp)	#push intlit to stack
	subu  $sp, $sp, 4		#update SP after pushing intlit
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 1
	syscall
	.data
.L0:	.asciiz "hello world"	# store stringLit
	.text
	la    $t0, .L0
	sw    $t0, 0($sp)	#PUSH stringlit onto stack
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP value for write stmt
	li    $v0, 4
	syscall
_main_Exit:
	lw    $ra, -4($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -8($fp)	#restore FP
	move  $sp, $t0		#restore SP
	li    $v0, 10		#load exit code for syscall
	syscall		#only do this for main
