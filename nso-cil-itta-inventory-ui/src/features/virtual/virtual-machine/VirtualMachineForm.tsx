import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { VIRTUAL_MACHINE_POWER_STATE_OPTIONS, VIRTUAL_MACHINE_CRITICALITY_OPTIONS, VirtualMachinePowerState, VirtualMachineCriticality } from './types';
import { validateVirtualMachine, cleanVirtualMachineValues } from './schema';
import { VIRTUALMACHINE_DEFAULTS } from './types';
import type { VirtualMachineFormValues, VirtualMachineInput } from './types';

interface Props {
  initial?: VirtualMachineFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: VirtualMachineInput) => void;
  onCancel?: () => void;
}

export function VirtualMachineForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<VirtualMachineFormValues>({ ...VIRTUALMACHINE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof VirtualMachineFormValues>(key: K, value: VirtualMachineFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateVirtualMachine(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanVirtualMachineValues(values as Record<string, unknown>) as unknown as VirtualMachineInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Virtual Machine form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Hypervisor Id" htmlFor="hypervisorId" error={errors.hypervisorId} required={true}>
        <Input
          id="hypervisorId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.hypervisorId ?? ''}
          onChange={(v) => set('hypervisorId', v)}
        />
      </FormField>
      <FormField label="Vm Name" htmlFor="vmName" error={errors.vmName} required={true}>
        <Input
          id="vmName"
          value={values.vmName ?? ''}
          maxLength={255}
          onChange={(v) => set('vmName', v)}
        />
      </FormField>
      <FormField label="Vcpu" htmlFor="vcpu" error={errors.vcpu} required={true}>
        <NumberInput
          id="vcpu"
          value={values.vcpu}
          step={1}
          onChange={(v) => set('vcpu', v)}
        />
      </FormField>
      <FormField label="Memory Gb" htmlFor="memoryGb" error={errors.memoryGb} required={true}>
        <NumberInput
          id="memoryGb"
          value={values.memoryGb}
          step={1}
          onChange={(v) => set('memoryGb', v)}
        />
      </FormField>
      <FormField label="Disk Gb" htmlFor="diskGb" error={errors.diskGb} required={false}>
        <NumberInput
          id="diskGb"
          value={values.diskGb}
          step={1}
          onChange={(v) => set('diskGb', v)}
        />
      </FormField>
      <FormField label="Power State" htmlFor="powerState" error={errors.powerState} required={false}>
        <Select
          id="powerState"
          value={values.powerState ?? ''}
          options={VIRTUAL_MACHINE_POWER_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('powerState', (v || undefined) as VirtualMachinePowerState | undefined)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={VIRTUAL_MACHINE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as VirtualMachineCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
