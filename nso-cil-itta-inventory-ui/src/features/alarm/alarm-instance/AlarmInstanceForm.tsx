import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { ALARM_INSTANCE_ALARM_STATE_OPTIONS, ALARM_INSTANCE_LIFECYCLE_STAGE_OPTIONS, AlarmInstanceAlarmState, AlarmInstanceLifecycleStage } from './types';
import { validateAlarmInstance, cleanAlarmInstanceValues } from './schema';
import { ALARMINSTANCE_DEFAULTS } from './types';
import type { AlarmInstanceFormValues, AlarmInstanceInput } from './types';

interface Props {
  initial?: AlarmInstanceFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: AlarmInstanceInput) => void;
  onCancel?: () => void;
}

export function AlarmInstanceForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<AlarmInstanceFormValues>({ ...ALARMINSTANCE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof AlarmInstanceFormValues>(key: K, value: AlarmInstanceFormValues[K]) {
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
    const result = validateAlarmInstance(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanAlarmInstanceValues(values as Record<string, unknown>) as unknown as AlarmInstanceInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Alarm Instance form">
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
      <FormField label="Definition Id" htmlFor="definitionId" error={errors.definitionId} required={true}>
        <Input
          id="definitionId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.definitionId ?? ''}
          onChange={(v) => set('definitionId', v)}
        />
      </FormField>
      <FormField label="Source Ref" htmlFor="sourceRef" error={errors.sourceRef} required={true}>
        <Input
          id="sourceRef"
          value={values.sourceRef ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceRef', v)}
        />
      </FormField>
      <FormField label="Raised At" htmlFor="raisedAt" error={errors.raisedAt} required={true}>
        <DateTimeInput
          id="raisedAt"
          value={values.raisedAt ?? ''}
          onChange={(v) => set('raisedAt', v)}
        />
      </FormField>
      <FormField label="Cleared At" htmlFor="clearedAt" error={errors.clearedAt} required={false}>
        <DateTimeInput
          id="clearedAt"
          value={values.clearedAt ?? ''}
          onChange={(v) => set('clearedAt', v)}
        />
      </FormField>
      <FormField label="Acked By" htmlFor="ackedBy" error={errors.ackedBy} required={false}>
        <Input
          id="ackedBy"
          value={values.ackedBy ?? ''}
          maxLength={255}
          onChange={(v) => set('ackedBy', v)}
        />
      </FormField>
      <FormField label="Alarm State" htmlFor="alarmState" error={errors.alarmState} required={false}>
        <Select
          id="alarmState"
          value={values.alarmState ?? ''}
          options={ALARM_INSTANCE_ALARM_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('alarmState', (v || undefined) as AlarmInstanceAlarmState | undefined)}
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
          options={ALARM_INSTANCE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as AlarmInstanceLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
