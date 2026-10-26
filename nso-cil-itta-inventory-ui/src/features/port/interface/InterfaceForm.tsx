import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { INTERFACE_OPER_STATE_OPTIONS, INTERFACE_CRITICALITY_OPTIONS, InterfaceOperState, InterfaceCriticality } from './types';
import { validateInterface, cleanInterfaceValues } from './schema';
import { INTERFACE_DEFAULTS } from './types';
import type { InterfaceFormValues, InterfaceInput } from './types';

interface Props {
  initial?: InterfaceFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: InterfaceInput) => void;
  onCancel?: () => void;
}

export function InterfaceForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<InterfaceFormValues>({ ...INTERFACE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof InterfaceFormValues>(key: K, value: InterfaceFormValues[K]) {
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
    const result = validateInterface(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanInterfaceValues(values as Record<string, unknown>) as unknown as InterfaceInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Interface form">
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
      <FormField label="Port Id" htmlFor="portId" error={errors.portId} required={true}>
        <Input
          id="portId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.portId ?? ''}
          onChange={(v) => set('portId', v)}
        />
      </FormField>
      <FormField label="If Name" htmlFor="ifName" error={errors.ifName} required={true}>
        <Input
          id="ifName"
          value={values.ifName ?? ''}
          maxLength={255}
          onChange={(v) => set('ifName', v)}
        />
      </FormField>
      <FormField label="If Index" htmlFor="ifIndex" error={errors.ifIndex} required={false}>
        <NumberInput
          id="ifIndex"
          value={values.ifIndex}
          step={1}
          onChange={(v) => set('ifIndex', v)}
        />
      </FormField>
      <FormField label="Mac Address" htmlFor="macAddress" error={errors.macAddress} required={false}>
        <Input
          id="macAddress"
          value={values.macAddress ?? ''}
          maxLength={255}
          onChange={(v) => set('macAddress', v)}
        />
      </FormField>
      <FormField label="Mtu" htmlFor="mtu" error={errors.mtu} required={false}>
        <NumberInput
          id="mtu"
          value={values.mtu}
          step={1}
          onChange={(v) => set('mtu', v)}
        />
      </FormField>
      <FormField label="Oper State" htmlFor="operState" error={errors.operState} required={false}>
        <Select
          id="operState"
          value={values.operState ?? ''}
          options={INTERFACE_OPER_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('operState', (v || undefined) as InterfaceOperState | undefined)}
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
          options={INTERFACE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as InterfaceCriticality | undefined)}
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
