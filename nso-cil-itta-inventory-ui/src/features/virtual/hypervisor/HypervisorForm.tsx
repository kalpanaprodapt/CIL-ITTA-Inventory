import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { HYPERVISOR_HYPERVISOR_TYPE_OPTIONS, HYPERVISOR_CRITICALITY_OPTIONS, HypervisorHypervisorType, HypervisorCriticality } from './types';
import { validateHypervisor, cleanHypervisorValues } from './schema';
import { HYPERVISOR_DEFAULTS } from './types';
import type { HypervisorFormValues, HypervisorInput } from './types';

interface Props {
  initial?: HypervisorFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: HypervisorInput) => void;
  onCancel?: () => void;
}

export function HypervisorForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<HypervisorFormValues>({ ...HYPERVISOR_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof HypervisorFormValues>(key: K, value: HypervisorFormValues[K]) {
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
    const result = validateHypervisor(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanHypervisorValues(values as Record<string, unknown>) as unknown as HypervisorInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Hypervisor form">
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
      <FormField label="Host Device Id" htmlFor="hostDeviceId" error={errors.hostDeviceId} required={true}>
        <Input
          id="hostDeviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.hostDeviceId ?? ''}
          onChange={(v) => set('hostDeviceId', v)}
        />
      </FormField>
      <FormField label="Hypervisor Type" htmlFor="hypervisorType" error={errors.hypervisorType} required={false}>
        <Select
          id="hypervisorType"
          value={values.hypervisorType ?? ''}
          options={HYPERVISOR_HYPERVISOR_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('hypervisorType', (v || undefined) as HypervisorHypervisorType | undefined)}
        />
      </FormField>
      <FormField label="Hv Version" htmlFor="hvVersion" error={errors.hvVersion} required={false}>
        <Input
          id="hvVersion"
          value={values.hvVersion ?? ''}
          maxLength={255}
          onChange={(v) => set('hvVersion', v)}
        />
      </FormField>
      <FormField label="Vcpu Capacity" htmlFor="vcpuCapacity" error={errors.vcpuCapacity} required={false}>
        <NumberInput
          id="vcpuCapacity"
          value={values.vcpuCapacity}
          step={1}
          onChange={(v) => set('vcpuCapacity', v)}
        />
      </FormField>
      <FormField label="Memory Gb Capacity" htmlFor="memoryGbCapacity" error={errors.memoryGbCapacity} required={false}>
        <NumberInput
          id="memoryGbCapacity"
          value={values.memoryGbCapacity}
          step={1}
          onChange={(v) => set('memoryGbCapacity', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={HYPERVISOR_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as HypervisorCriticality | undefined)}
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
