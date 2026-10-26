import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';

import { validatePseudoWire, cleanPseudoWireValues } from './schema';
import { PSEUDOWIRE_DEFAULTS } from './types';
import type { PseudoWireFormValues, PseudoWireInput } from './types';

interface Props {
  initial?: PseudoWireFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: PseudoWireInput) => void;
  onCancel?: () => void;
}

export function PseudoWireForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<PseudoWireFormValues>({ ...PSEUDOWIRE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof PseudoWireFormValues>(key: K, value: PseudoWireFormValues[K]) {
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
    const result = validatePseudoWire(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanPseudoWireValues(values as Record<string, unknown>) as unknown as PseudoWireInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Pseudo Wire form">
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
      <FormField label="Vc Id" htmlFor="vcId" error={errors.vcId} required={true}>
        <NumberInput
          id="vcId"
          value={values.vcId}
          step={1}
          onChange={(v) => set('vcId', v)}
        />
      </FormField>
      <FormField label="A End Id" htmlFor="aEndId" error={errors.aEndId} required={true}>
        <Input
          id="aEndId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.aEndId ?? ''}
          onChange={(v) => set('aEndId', v)}
        />
      </FormField>
      <FormField label="Z End Id" htmlFor="zEndId" error={errors.zEndId} required={true}>
        <Input
          id="zEndId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.zEndId ?? ''}
          onChange={(v) => set('zEndId', v)}
        />
      </FormField>
      <FormField label="Encapsulation" htmlFor="encapsulation" error={errors.encapsulation} required={false}>
        <Input
          id="encapsulation"
          value={values.encapsulation ?? ''}
          maxLength={255}
          onChange={(v) => set('encapsulation', v)}
        />
      </FormField>
      <FormField label="Control Word" htmlFor="controlWord" error={errors.controlWord} required={false}>
        <Checkbox
          id="controlWord"
          checked={Boolean(values.controlWord)}
          onChange={(v) => set('controlWord', v)}
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
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
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
