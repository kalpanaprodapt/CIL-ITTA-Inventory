import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { VLAN_VLAN_TYPE_OPTIONS, VLAN_LIFECYCLE_STAGE_OPTIONS, VlanVlanType, VlanLifecycleStage } from './types';
import { validateVlan, cleanVlanValues } from './schema';
import { VLAN_DEFAULTS } from './types';
import type { VlanFormValues, VlanInput } from './types';

interface Props {
  initial?: VlanFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: VlanInput) => void;
  onCancel?: () => void;
}

export function VlanForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<VlanFormValues>({ ...VLAN_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof VlanFormValues>(key: K, value: VlanFormValues[K]) {
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
    const result = validateVlan(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanVlanValues(values as Record<string, unknown>) as unknown as VlanInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Vlan form">
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
      <FormField label="Vlan Id" htmlFor="vlanId" error={errors.vlanId} required={true}>
        <NumberInput
          id="vlanId"
          value={values.vlanId}
          step={1}
          onChange={(v) => set('vlanId', v)}
        />
      </FormField>
      <FormField label="Vlan Name" htmlFor="vlanName" error={errors.vlanName} required={true}>
        <Input
          id="vlanName"
          value={values.vlanName ?? ''}
          maxLength={255}
          onChange={(v) => set('vlanName', v)}
        />
      </FormField>
      <FormField label="Vlan Type" htmlFor="vlanType" error={errors.vlanType} required={false}>
        <Select
          id="vlanType"
          value={values.vlanType ?? ''}
          options={VLAN_VLAN_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('vlanType', (v || undefined) as VlanVlanType | undefined)}
        />
      </FormField>
      <FormField label="Pool Id" htmlFor="poolId" error={errors.poolId} required={false}>
        <Input
          id="poolId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.poolId ?? ''}
          onChange={(v) => set('poolId', v)}
        />
      </FormField>
      <FormField label="Stp Mode" htmlFor="stpMode" error={errors.stpMode} required={false}>
        <Input
          id="stpMode"
          value={values.stpMode ?? ''}
          maxLength={255}
          onChange={(v) => set('stpMode', v)}
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
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={VLAN_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as VlanLifecycleStage | undefined)}
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
