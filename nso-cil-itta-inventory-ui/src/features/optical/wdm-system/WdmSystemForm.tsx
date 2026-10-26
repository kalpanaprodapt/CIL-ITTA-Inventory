import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { WDM_SYSTEM_BAND_OPTIONS, WDM_SYSTEM_LIFECYCLE_STAGE_OPTIONS, WdmSystemBand, WdmSystemLifecycleStage } from './types';
import { validateWdmSystem, cleanWdmSystemValues } from './schema';
import { WDMSYSTEM_DEFAULTS } from './types';
import type { WdmSystemFormValues, WdmSystemInput } from './types';

interface Props {
  initial?: WdmSystemFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: WdmSystemInput) => void;
  onCancel?: () => void;
}

export function WdmSystemForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<WdmSystemFormValues>({ ...WDMSYSTEM_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof WdmSystemFormValues>(key: K, value: WdmSystemFormValues[K]) {
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
    const result = validateWdmSystem(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanWdmSystemValues(values as Record<string, unknown>) as unknown as WdmSystemInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Wdm System form">
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
      <FormField label="System Name" htmlFor="systemName" error={errors.systemName} required={true}>
        <Input
          id="systemName"
          value={values.systemName ?? ''}
          maxLength={255}
          onChange={(v) => set('systemName', v)}
        />
      </FormField>
      <FormField label="Channel Capacity" htmlFor="channelCapacity" error={errors.channelCapacity} required={true}>
        <NumberInput
          id="channelCapacity"
          value={values.channelCapacity}
          step={1}
          onChange={(v) => set('channelCapacity', v)}
        />
      </FormField>
      <FormField label="Band" htmlFor="band" error={errors.band} required={false}>
        <Select
          id="band"
          value={values.band ?? ''}
          options={WDM_SYSTEM_BAND_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('band', (v || undefined) as WdmSystemBand | undefined)}
        />
      </FormField>
      <FormField label="Span Count" htmlFor="spanCount" error={errors.spanCount} required={false}>
        <NumberInput
          id="spanCount"
          value={values.spanCount}
          step={1}
          onChange={(v) => set('spanCount', v)}
        />
      </FormField>
      <FormField label="Vendor Id" htmlFor="vendorId" error={errors.vendorId} required={false}>
        <Input
          id="vendorId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.vendorId ?? ''}
          onChange={(v) => set('vendorId', v)}
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
          options={WDM_SYSTEM_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as WdmSystemLifecycleStage | undefined)}
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
